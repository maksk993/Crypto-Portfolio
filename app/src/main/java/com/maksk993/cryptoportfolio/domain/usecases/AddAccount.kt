package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.repository.AccountRepository

class AddAccount(private val accountRepository: AccountRepository) {
    suspend fun execute(account: Account){
        accountRepository.add(account)
    }
}