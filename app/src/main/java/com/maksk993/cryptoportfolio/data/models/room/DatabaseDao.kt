package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.TransactionDbEntity

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAsset(assetDbEntity: AssetDbEntity)

    @Query("DELETE FROM portfolio_assets WHERE symbol = :symbol")
    suspend fun removeAsset(symbol : String)

    @Query("SELECT * FROM portfolio_assets")
    fun getAllAssetsFromPortfolio() : MutableList<AssetDbEntity?>

    @Insert
    suspend fun saveTransaction(transactionDbEntity: TransactionDbEntity)

    @Query("SELECT * FROM transaction_history")
    suspend fun getTransactions() : List<TransactionDbEntity?>

    @Query("SELECT * FROM transaction_history WHERE symbol = :symbol")
    suspend fun getTransactionsBySymbol(symbol: String) : List<TransactionDbEntity?>
}