package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Account

interface AccountRepository {
    suspend fun add(account: Account)
    suspend fun getAccounts(): List<Account?>
    fun getLastAccount(): Account
    fun saveLastAccount(account: Account)
}