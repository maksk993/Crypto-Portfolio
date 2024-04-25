package com.maksk993.cryptoportfolio.data.models.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.models.TransactionType

@Entity(tableName = "transaction_history")
data class TransactionDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val symbol : String,
    val amount : Float,
    val transactionPrice : Float,
    val type : TransactionType
) {
    fun toTransaction() : Transaction {
        return Transaction(symbol = symbol, transactionPrice = transactionPrice, type = type, amount = amount)
    }

    companion object{
        fun toDbEntity(transaction: Transaction) : TransactionDbEntity {
            return TransactionDbEntity(
                symbol = transaction.symbol,
                amount = transaction.amount,
                transactionPrice = transaction.transactionPrice,
                type = transaction.type,
                id = 0)
        }
    }
}