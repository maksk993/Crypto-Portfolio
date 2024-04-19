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
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList


class MainViewModel : ViewModel() {
    private val getPrices = GetPricesFromCoinMarketCap(CryptoRepositoryImpl())
    private val addAssetToPortfolio = AddAssetToPortfolio(Database.dbRepository)
    private val getAssetsFromPortfolio = GetAssetsFromPortfolio(Database.dbRepository)

    private val nextFragment : MutableLiveData<FindFragmentById> = MutableLiveData()
    var addedAsset : MutableLiveData<AssetItem> = MutableLiveData()
    val item : MutableLiveData<AssetItem> = MutableLiveData()
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
            item.value = AssetItem(
                symbol,
                price
            )
            getAssetsFromPortfolio()
        }
    }

    fun getAssetsFromPortfolio() {
        viewModelScope.launch {
            assetsInPortfolio.value = getAssetsFromPortfolio.execute()
            currentBalance.value = assetsInPortfolio.value!!.stream()
                .mapToDouble{it!!.price.toDouble() * it.amount}
                .sum()
                .toFloat()
        }
    }

    fun setActualPrices() : LiveData<MutableMap<String, Float>> {
        for (i in CryptoRepositoryImpl.actualPrices){
            actualPrices.value!![i.key] = i.value
        }
        return actualPrices
    }

    fun openFragment(id : FindFragmentById){
        nextFragment.value = id
    }

    fun shouldNewFragmentBeOpened() : LiveData<FindFragmentById> {
        return nextFragment
    }

    fun getItem() : LiveData<AssetItem> {
        return item
    }

    fun setAddedAsset(assetItem: AssetItem){
        addedAsset = MutableLiveData(assetItem)
    }

    fun getAddedAsset() : LiveData<AssetItem> {
        return addedAsset
    }

    fun addAssetToPortfolio(amountString : String){
        val amount : Float = amountString.toFloat()
        for (i in assetsInPortfolio.value!!){
            if (i!!.symbol == addedAsset.value!!.symbol) {
                viewModelScope.launch {
                    addAssetToPortfolio.execute(addedAsset.value!!, amount + i.amount)
                    getAssetsFromPortfolio()
                }
                return
            }
        }
        viewModelScope.launch {
            addAssetToPortfolio.execute(addedAsset.value!!, amount)
            getAssetsFromPortfolio()
        }
    }
}