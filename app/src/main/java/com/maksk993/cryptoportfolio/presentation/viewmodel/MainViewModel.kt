package com.maksk993.cryptoportfolio.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.usecases.AddAssetToPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
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
            _updatingAsset.value = AssetItem(symbol, price)
            getAddedAssetsFromDb() // ???
            setActualPrices()
        }
    }

    fun getAddedAssetsFromDb() {
        viewModelScope.launch {
            _assetsInPortfolio.value = getAssetsFromPortfolio.execute(_currentAccount.value!!)
            calculateBalance()
        }
    }

    private fun calculateBalance(){
        _currentBalance.value = _assetsInPortfolio.value!!.stream()
            .mapToDouble{it!!.price.toDouble() * it.amount}
            .filter { it >= 0F }
            .sum()
            .toFloat()
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
            amount = amount,
            account = _currentAccount.value!!)
    }

    fun addAssetToPortfolio(amount: Float) {
        for (i in _assetsInPortfolio.value!!){
            if (i!!.symbol == _focusedAsset.value!!.symbol) {
                viewModelScope.launch {
                    _focusedAsset.value!!.amount = amount + i.amount
                    addAssetToPortfolio.execute(_focusedAsset.value!!)
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
            removeAssetFromPortfolio.execute(_focusedAsset.value!!.toAssetItem(), _currentAccount.value!!)
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
            _transactions.value = getTransactions.execute(_currentAccount.value!!).toMutableList()
        }
    }

    fun saveTransaction(transactionPrice : Float, amount: Float = -_focusedAsset.value!!.amount, type : TransactionType){
        val transaction = Transaction(
            symbol = _focusedAsset.value!!.symbol,
            amount = amount,
            transactionPrice = transactionPrice,
            type = type,
            account = _currentAccount.value!!
        )
        _transactions.value!!.add(transaction)
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
}