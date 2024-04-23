package com.maksk993.cryptoportfolio.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.data.models.room.Database
import com.maksk993.cryptoportfolio.domain.usecases.AddAssetToPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
import com.maksk993.cryptoportfolio.domain.usecases.GetAssetsFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.RemoveAssetFromPortfolio
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList


class MainViewModel : ViewModel() {
    private val getPrices = GetPricesFromCoinMarketCap(CryptoRepositoryImpl())
    private val addAssetToPortfolio = AddAssetToPortfolio(Database.dbRepository)
    private val removeAssetFromPortfolio = RemoveAssetFromPortfolio(Database.dbRepository)
    private val getAssetsFromPortfolio = GetAssetsFromPortfolio(Database.dbRepository)

    private val nextFragment : MutableLiveData<FindFragmentById> = MutableLiveData()
    var focusedAsset : MutableLiveData<PortfolioAssetItem> = MutableLiveData()
    var removedAsset : MutableLiveData<AssetItem> = MutableLiveData()
    private val updatingAsset : MutableLiveData<AssetItem> = MutableLiveData()
    val actualPrices: MutableLiveData<MutableMap<String, Float>> = MutableLiveData(HashMap())
    val assetsInPortfolio : MutableLiveData<MutableList<PortfolioAssetItem?>> = MutableLiveData(ArrayList())
    val currentBalance : MutableLiveData<Float> = MutableLiveData(0F)

    fun startReceivingData(){
        viewModelScope.launch {
            while(true) {
                currentBalance.value = 0F
                Log.d("PRICES", "Coroutine is started.")
                getPricesFromCoinMarketCap()
                delay(30_000)
            }
        }
    }

    private fun getPricesFromCoinMarketCap(){
        getPrices.execute { symbol, price ->
            updatingAsset.value = AssetItem(symbol, price)
            getAssetsFromPortfolio()
            setActualPrices()
        }
    }

    fun getAssetsFromPortfolio() {
        viewModelScope.launch {
            assetsInPortfolio.value = getAssetsFromPortfolio.execute()
            currentBalance.value = assetsInPortfolio.value!!.stream()
                .mapToDouble{it!!.price.toDouble() * it.amount}
                .filter { it >= 0F }
                .sum()
                .toFloat()
        }
    }

    private fun setActualPrices() {
        for (i in CryptoRepositoryImpl.actualPrices){
            actualPrices.value!![i.key] = i.value
        }
    }

    fun openFragment(id : FindFragmentById){
        nextFragment.value = id
    }

    fun shouldNewFragmentBeOpened() : LiveData<FindFragmentById> {
        return nextFragment
    }

    fun getUpdatingAsset() : LiveData<AssetItem> {
        return updatingAsset
    }

    fun setFocusedAsset(assetItem: AssetItem, amount : Float = 0F){
        focusedAsset = MutableLiveData(
            PortfolioAssetItem(
                symbol = assetItem.symbol,
                price = actualPrices.value!![assetItem.symbol]?:-1F,
                image = assetItem.image,
                amount = amount)
        )
    }

    fun getFocusedAsset() : LiveData<PortfolioAssetItem> {
        return focusedAsset
    }

    fun addAssetToPortfolio(amountString : String){
        val amount : Float = amountString.toFloat()
        for (i in assetsInPortfolio.value!!){
            if (i!!.symbol == focusedAsset.value!!.symbol) {
                viewModelScope.launch {
                    if (amount + i.amount <= 0F) {
                        focusedAsset.value!!.amount = amount
                        addAssetToPortfolio.execute(focusedAsset.value!!)
                    }
                    else {
                        focusedAsset.value!!.amount = amount + i.amount
                        addAssetToPortfolio.execute(focusedAsset.value!!)
                    }
                    getAssetsFromPortfolio()
                }
                return
            }
        }
        viewModelScope.launch {
            focusedAsset.value!!.amount = amount
            addAssetToPortfolio.execute(focusedAsset.value!!)
            getAssetsFromPortfolio()
        }
    }

    fun removeAssetFromPortfolio(){
        viewModelScope.launch {
            removeAssetFromPortfolio.execute(focusedAsset.value!!.toAssetItem())
            getAssetsFromPortfolio()
            removedAsset.value = focusedAsset.value!!.toAssetItem()
        }
    }

    fun getAmountOf(symbol : String): Float {
        for (i in assetsInPortfolio.value!!){
            if (i!!.symbol == symbol) return i.amount
        }
        return 0F
    }
}