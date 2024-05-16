package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.repository.AccountRepository

class SaveLastAccount(private val repository: AccountRepository) {
    fun execute(account: Account){
        repository.saveLastAccount(account = account)
    }
}