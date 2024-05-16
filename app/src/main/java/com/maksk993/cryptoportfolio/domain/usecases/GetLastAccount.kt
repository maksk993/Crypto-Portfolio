package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.repository.AccountRepository

class GetLastAccount(private val repository: AccountRepository) {
    fun execute(): Account {
        return repository.getLastAccount()
    }
}