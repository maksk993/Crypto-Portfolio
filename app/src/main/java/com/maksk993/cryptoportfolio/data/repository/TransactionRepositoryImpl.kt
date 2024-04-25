package com.maksk993.cryptoportfolio.data.repository

import com.maksk993.cryptoportfolio.data.models.room.AssetsDbDao
import com.maksk993.cryptoportfolio.data.models.room.entities.TransactionDbEntity
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val dao : AssetsDbDao) : TransactionRepository {
    override suspend fun getTransactions(): List<Transaction?> {
        return dao.getTransactions().map { it?.toTransaction() }
    }

    override suspend fun getTransactions(symbol : String): List<Transaction?> {
        return dao.getTransactionsBySymbol(symbol).map { it?.toTransaction() }
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(TransactionDbEntity.toDbEntity(transaction))
    }
}