package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Transaction
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository

class SaveTransaction(private val repository: TransactionRepository) {
    suspend fun execute(transaction: Transaction){
        repository.saveTransaction(transaction)
    }
}