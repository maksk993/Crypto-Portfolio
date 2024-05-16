package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maksk993.cryptoportfolio.data.models.room.entities.AccountDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.TransactionDbEntity

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsset(assetDbEntity: AssetDbEntity)

    @Query("DELETE FROM portfolio_assets WHERE symbol = :symbol AND accountName = :accountName")
    suspend fun removeAsset(symbol : String, accountName: String)

    @Query("SELECT * FROM portfolio_assets WHERE accountName = :accountName")
    fun getAllAssetsFromPortfolio(accountName: String) : MutableList<AssetDbEntity?>

    @Insert
    suspend fun saveTransaction(transactionDbEntity: TransactionDbEntity)

    @Query("SELECT * FROM transaction_history WHERE accountName = :accountName")
    suspend fun getTransactions(accountName: String) : List<TransactionDbEntity?>

    @Query("SELECT * FROM transaction_history WHERE symbol = :symbol AND accountName = :accountName")
    suspend fun getTransactionsBySymbol(symbol: String, accountName: String) : List<TransactionDbEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts")
    suspend fun getAccounts(): List<AccountDbEntity?>
}