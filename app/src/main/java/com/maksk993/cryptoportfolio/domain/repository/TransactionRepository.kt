package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Transaction

interface TransactionRepository {
    suspend fun getTransactions() : List<Transaction?>
    suspend fun getTransactions(symbol: String): List<Transaction?>
    suspend fun saveTransaction(transaction: Transaction)
}