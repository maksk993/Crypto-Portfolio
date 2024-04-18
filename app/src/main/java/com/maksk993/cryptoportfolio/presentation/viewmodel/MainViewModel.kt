package com.maksk993.cryptoportfolio.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.data.models.room.Database
import com.maksk993.cryptoportfolio.domain.usecases.AddAssetToPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val getPrices = GetPricesFromCoinMarketCap(CryptoRepositoryImpl())
    private val addAssetToPortfolio = AddAssetToPortfolio(Database.dbRepository)

    private val nextFragment : MutableLiveData<FindFragmentById> = MutableLiveData()
    var addedAsset : MutableLiveData<AssetItem> = MutableLiveData()
    val item : MutableLiveData<AssetItem> = MutableLiveData()

    fun openFragment(id : FindFragmentById){
        nextFragment.value = id
    }

    fun shouldNewFragmentBeOpened() : LiveData<FindFragmentById> {
        return nextFragment
    }

    fun getPricesFromCoinMarketCap(){
        getPrices.execute { symbol, price ->
            item.value = AssetItem(
                symbol,
                price
            )
        }
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
        viewModelScope.launch {
            addAssetToPortfolio.execute(addedAsset.value!!, amount)
        }
    }
}