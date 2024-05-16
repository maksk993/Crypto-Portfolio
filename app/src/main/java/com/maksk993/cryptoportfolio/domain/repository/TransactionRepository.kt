package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Transaction

interface TransactionRepository {
    suspend fun getTransactions(account: Account) : List<Transaction?>
    suspend fun getTransactions(symbol: String, account: Account): List<Transaction?>
    suspend fun saveTransaction(transaction: Transaction)
}