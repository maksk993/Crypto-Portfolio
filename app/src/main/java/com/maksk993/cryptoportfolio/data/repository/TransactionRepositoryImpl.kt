package com.maksk993.cryptoportfolio.data.repository

import com.maksk993.cryptoportfolio.data.models.room.DatabaseDao
import com.maksk993.cryptoportfolio.data.models.room.entities.TransactionDbEntity
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository

class TransactionRepositoryImpl(private val dao : DatabaseDao) : TransactionRepository {
    override suspend fun getTransactions(account: Account): List<Transaction?> {
        return dao.getTransactions(account.name).map { it?.toTransaction() }
    }

    override suspend fun getTransactions(symbol : String, account: Account): List<Transaction?> {
        // TO DO
        return dao.getTransactionsBySymbol(symbol, account.name).map { it?.toTransaction() }
    }

    override suspend fun saveTransaction(transaction: Transaction) {
        dao.saveTransaction(TransactionDbEntity.toDbEntity(transaction))
    }
}