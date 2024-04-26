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
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.models.TransactionType
import com.maksk993.cryptoportfolio.domain.usecases.GetAssetsFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetTransactions
import com.maksk993.cryptoportfolio.domain.usecases.RemoveAssetFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.SaveTransaction
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList


class MainViewModel : ViewModel() {
    private val getPrices = GetPricesFromCoinMarketCap(CryptoRepositoryImpl())
    private val addAssetToPortfolio = AddAssetToPortfolio(Database.dbRepository)
    private val removeAssetFromPortfolio = RemoveAssetFromPortfolio(Database.dbRepository)
    private val getAssetsFromPortfolio = GetAssetsFromPortfolio(Database.dbRepository)
    // HISTORY
    private val saveTransaction = SaveTransaction(Database.transactionRepository)
    private val getTransactions = GetTransactions(Database.transactionRepository)

    private val _nextFragment : MutableLiveData<FindFragmentById> = MutableLiveData()
    val nextFragment : LiveData<FindFragmentById> = _nextFragment

    private val _focusedAsset : MutableLiveData<PortfolioAssetItem> = MutableLiveData()
    val focusedAsset: LiveData<PortfolioAssetItem> = _focusedAsset

    private val _removedAsset : MutableLiveData<AssetItem> = MutableLiveData()
    val removedAsset: LiveData<AssetItem> = _removedAsset

    private val _updatingAsset : MutableLiveData<AssetItem> = MutableLiveData()
    val updatingAsset : LiveData<AssetItem> = _updatingAsset

    private val _actualPrices: MutableLiveData<MutableMap<String, Float>> = MutableLiveData(HashMap())
    val actualPrices: LiveData<MutableMap<String, Float>> = _actualPrices

    private val _assetsInPortfolio : MutableLiveData<MutableList<PortfolioAssetItem?>> = MutableLiveData(ArrayList())
    val assetsInPortfolio : LiveData<MutableList<PortfolioAssetItem?>> = _assetsInPortfolio

    private val _currentBalance : MutableLiveData<Float> = MutableLiveData(0F)
    val currentBalance : LiveData<Float> = _currentBalance

    // HISTORY
    private val _transactions : MutableLiveData<MutableList<Transaction?>> = MutableLiveData(ArrayList())
    val transactions : LiveData<MutableList<Transaction?>> = _transactions

    fun startReceivingData(){
        viewModelScope.launch {
            while(true) {
                _currentBalance.value = 0F
                Log.d("PRICES", "Coroutine is started.")
                getPricesFromCoinMarketCap()
                delay(30_000)
            }
        }
    }

    fun openFragment(id : FindFragmentById){ _nextFragment.value = id }

    private fun getPricesFromCoinMarketCap(){
        getPrices.execute { symbol, price ->
            _updatingAsset.value = AssetItem(symbol, price)
            getAddedAssetsFromDb()
            setActualPrices()
        }
    }

    fun getAddedAssetsFromDb() {
        viewModelScope.launch {
            _assetsInPortfolio.value = getAssetsFromPortfolio.execute()
            _currentBalance.value = _assetsInPortfolio.value!!.stream()
                .mapToDouble{it!!.price.toDouble() * it.amount}
                .filter { it >= 0F }
                .sum()
                .toFloat()
        }
    }

    private fun setActualPrices() {
        for (i in CryptoRepositoryImpl.actualPrices){
            _actualPrices.value!![i.key] = i.value
        }
    }

    fun setFocusedAsset(assetItem: AssetItem, amount : Float = 0F){
        _focusedAsset.value = PortfolioAssetItem(
            symbol = assetItem.symbol,
            price = _actualPrices.value!![assetItem.symbol]?:-1F,
            image = assetItem.image,
            amount = amount)
    }

    fun addAssetToPortfolio(amount: Float) {
        for (i in _assetsInPortfolio.value!!){
            if (i!!.symbol == _focusedAsset.value!!.symbol) {
                viewModelScope.launch {
                    if (amount + i.amount <= 0F) {
                        _focusedAsset.value!!.amount = amount
                        addAssetToPortfolio.execute(_focusedAsset.value!!)
                    }
                    else {
                        _focusedAsset.value!!.amount = amount + i.amount
                        addAssetToPortfolio.execute(_focusedAsset.value!!)
                    }
                    getAddedAssetsFromDb()
                }
                return
            }
        }
        viewModelScope.launch {
            _focusedAsset.value!!.amount = amount
            addAssetToPortfolio.execute(_focusedAsset.value!!)
            getAddedAssetsFromDb()
        }
    }

    fun removeAssetFromPortfolio(){
        viewModelScope.launch {
            removeAssetFromPortfolio.execute(_focusedAsset.value!!.toAssetItem())
            getAddedAssetsFromDb()
            _removedAsset.value = _focusedAsset.value!!.toAssetItem()
        }
    }

    fun getAmountOf(symbol : String): Float {
        for (i in _assetsInPortfolio.value!!){
            if (i!!.symbol == symbol) return i.amount
        }
        return 0F
    }

    // HISTORY
    fun getTransactionsFromDb(){
        viewModelScope.launch {
            _transactions.value = getTransactions.execute().toMutableList()
        }
    }

    fun saveTransaction(transactionPrice : Float, amount: Float = -_focusedAsset.value!!.amount, type : TransactionType){
        val transaction = Transaction(
            symbol = _focusedAsset.value!!.symbol,
            amount = amount,
            transactionPrice = transactionPrice,
            type = type
        )
        _transactions.value!!.add(transaction)
        viewModelScope.launch {
            saveTransaction.execute(transaction)
        }
    }
}