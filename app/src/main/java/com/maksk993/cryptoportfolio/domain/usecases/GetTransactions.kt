package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository

class GetTransactions(private val repository: TransactionRepository) {
    suspend fun execute() : List<Transaction?> {
        return repository.getTransactions()
    }

    suspend fun execute(symbol : String) : List<Transaction?> {
        return repository.getTransactions(symbol)
    }
}