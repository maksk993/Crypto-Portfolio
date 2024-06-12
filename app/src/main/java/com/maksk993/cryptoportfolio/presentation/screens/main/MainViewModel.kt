package com.maksk993.cryptoportfolio.presentation.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.usecases.AddAssetToPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.models.TransactionType
import com.maksk993.cryptoportfolio.domain.usecases.AddAccount
import com.maksk993.cryptoportfolio.domain.usecases.GetAccounts
import com.maksk993.cryptoportfolio.domain.usecases.GetAssetsFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetLastAccount
import com.maksk993.cryptoportfolio.domain.usecases.GetSavedAppTheme
import com.maksk993.cryptoportfolio.domain.usecases.GetTransactions
import com.maksk993.cryptoportfolio.domain.usecases.RemoveAssetFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.SaveLastAccount
import com.maksk993.cryptoportfolio.domain.usecases.SaveTransaction
import com.maksk993.cryptoportfolio.domain.usecases.SwitchAppTheme
import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPrices: GetPricesFromCoinMarketCap,
    private val addAssetToPortfolio: AddAssetToPortfolio,
    private val removeAssetFromPortfolio: RemoveAssetFromPortfolio,
    private val getAssetsFromPortfolio: GetAssetsFromPortfolio,
    private val saveTransaction: SaveTransaction,
    private val getTransactions: GetTransactions,
    private val getTheme: GetSavedAppTheme,
    private val switchAppTheme: SwitchAppTheme,
    private val addAccount: AddAccount,
    private val getAccounts: GetAccounts,
    private val saveLastAccount: SaveLastAccount,
    private val getLastAccount: GetLastAccount
) : ViewModel() {
    private val _currentAccount: MutableLiveData<Account> = MutableLiveData()
    val currentAccount: LiveData<Account> = _currentAccount

    private val _nextFragment : MutableLiveData<FindFragmentById> = MutableLiveData()
    val nextFragment : LiveData<FindFragmentById> = _nextFragment

    private val _focusedAsset : MutableLiveData<Asset> = MutableLiveData()
    val focusedAsset: LiveData<Asset> = _focusedAsset

    private val _removedAsset : MutableLiveData<Asset> = MutableLiveData()
    val removedAsset: LiveData<Asset> = _removedAsset

    private val _actualPrices: MutableLiveData<MutableMap<String, Float>> = MutableLiveData(HashMap())
    val actualPrices: LiveData<MutableMap<String, Float>> = _actualPrices

    private val _assetsInPortfolio : MutableLiveData<MutableList<Asset?>> = MutableLiveData(ArrayList())
    val assetsInPortfolio : LiveData<MutableList<Asset?>> = _assetsInPortfolio

    private val _currentBalance : MutableLiveData<Float> = MutableLiveData(0F)
    val currentBalance : LiveData<Float> = _currentBalance

    // HISTORY
    private val _transactions : MutableLiveData<MutableList<Transaction?>> = MutableLiveData(ArrayList())
    val transactions : LiveData<MutableList<Transaction?>> = _transactions

    // SETTINGS
    private val _nightMode: MutableLiveData<Boolean> = MutableLiveData(false)
    val nightMode: LiveData<Boolean> = _nightMode

    // ACCOUNTS
    private val _accounts : MutableLiveData<MutableList<Account?>> = MutableLiveData(ArrayList())
    val accounts : LiveData<MutableList<Account?>> = _accounts

    fun openFragment(id : FindFragmentById){ _nextFragment.value = id }

    fun createFragment(vararg ids: FindFragmentById){
        for (id in ids){
            FindFragmentById.newInstance(id)
        }
    }

    fun startReceivingData(){
        viewModelScope.launch {
            while(true) {
                _currentBalance.value = 0F
                Log.d("PRICES", "startReceivingData().")
                getPricesFromCoinMarketCap()
                delay(30_000)
            }
        }
    }

    private fun getPricesFromCoinMarketCap(){
        getPrices.execute { symbol, price ->
            val actualPricesMap = _actualPrices.value ?: mutableMapOf()
            actualPricesMap[symbol] = price
            for (i in _assetsInPortfolio.value ?: return@execute){
                if (i?.symbol == symbol) i?.price = price
            }
            _actualPrices.value = actualPricesMap
            calculateBalance()
        }
    }

    fun getAddedAssetsFromDb() {
        viewModelScope.launch {
            val assetList = getAssetsFromPortfolio.execute(_currentAccount.value ?: return@launch)
            for (i in assetList){
                i?.price = _actualPrices.value?.get(i?.symbol) ?: -1F
            }
            _assetsInPortfolio.value = assetList.toMutableList()
            calculateBalance()
        }
    }

    fun calculateBalance(){
        _assetsInPortfolio.value?.let { assetsInPortfolioValue ->
            _currentBalance.value = assetsInPortfolioValue.stream()
                .mapToDouble{ asset ->
                    asset?.let {
                        (asset.price.toDouble()) * asset.amount
                    } ?: 0.0
                }
                .filter { it >= 0F }
                .sum()
                .toFloat()
        }
    }

    fun setFocusedAsset(asset: Asset){
        _focusedAsset.value = Asset(
            symbol = asset.symbol,
            price = _actualPrices.value?.get(asset.symbol) ?: -1F,
            image = asset.image,
            amount = asset.amount,
            account = _currentAccount.value ?: return
        )
    }

    fun addAssetToPortfolio(amount: Float) {
        for (i in _assetsInPortfolio.value ?: return){
            if (i != null && i.symbol == _focusedAsset.value?.symbol) {
                viewModelScope.launch {
                    _focusedAsset.value?.let { focusedAssetValue ->
                        focusedAssetValue.amount = amount + i.amount
                        addAssetToPortfolio.execute(focusedAssetValue)
                        getAddedAssetsFromDb()
                    }
                }
                return
            }
        }
        viewModelScope.launch {
            _focusedAsset.value?.amount = amount
            addAssetToPortfolio.execute(_focusedAsset.value ?: return@launch)
            getAddedAssetsFromDb()
        }
    }

    fun removeAssetFromPortfolio(){
        viewModelScope.launch {
            removeAssetFromPortfolio.execute(
                _focusedAsset.value ?: return@launch,
                _currentAccount.value ?: return@launch
            )
            getAddedAssetsFromDb()
            _removedAsset.value = _focusedAsset.value ?: return@launch
        }
    }

    // HISTORY
    fun getTransactionsFromDb(){
        viewModelScope.launch {
            _currentAccount.value?.let {
                _transactions.value = getTransactions.execute(it).toMutableList()
            }
        }
    }

    fun saveTransaction(transactionPrice : Float, amount: Float = -_focusedAsset.value!!.amount, type : TransactionType){
        val transaction = Transaction(
            symbol = _focusedAsset.value?.symbol ?: return,
            amount = amount,
            transactionPrice = transactionPrice,
            type = type,
            account = _currentAccount.value ?: return
        )
        _transactions.value?.add(transaction)
        viewModelScope.launch {
            saveTransaction.execute(transaction)
        }
    }

    // SETTINGS
    fun switchTheme(){
        _nightMode.value = _nightMode.value != true
    }

    fun setSavedAppTheme(){
        _nightMode.value = isSavedAppThemeNight()
    }

    private fun isSavedAppThemeNight(): Boolean = getTheme.execute()

    fun setNightTheme(state: Boolean) = switchAppTheme.execute(state)

    // ACCOUNTS

    fun addNewAccount(account: Account): Boolean{
        _accounts.value?.let {
            if (!it.contains(account)) it.add(account)
            else return false
        }
        viewModelScope.launch {
            addAccount.execute(account = account)
            getAccountsFromDb()
        }
        return true
    }

    fun getAccountsFromDb(){
        viewModelScope.launch {
            _accounts.value = getAccounts.execute().toMutableList()
        }
    }

    fun setCurrentAccount(account: Account){
        _currentAccount.value = account
        saveLastAccount.execute(account)
    }

    fun getLastAccountName() {
        _currentAccount.value = getLastAccount.execute()
    }

    fun getAssetsBySearch(query: String): List<Asset> {
        val list = mutableListOf<Asset>()
        for (i in actualPrices.value ?: return list){
            if (i.key.startsWith(query.uppercase())) list.add(Asset(symbol = i.key, price = i.value))
        }
        return list
    }
    
}