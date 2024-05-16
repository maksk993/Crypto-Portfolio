package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository

class GetTransactions(private val repository: TransactionRepository) {
    suspend fun execute(account: Account) : List<Transaction?> {
        return repository.getTransactions(account)
    }

    suspend fun execute(symbol : String, account: Account) : List<Transaction?> {
        return repository.getTransactions(symbol, account)
    }
}