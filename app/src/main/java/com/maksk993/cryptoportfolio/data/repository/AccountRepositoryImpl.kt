package com.maksk993.cryptoportfolio.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.maksk993.cryptoportfolio.data.models.room.DatabaseDao
import com.maksk993.cryptoportfolio.data.models.room.entities.AccountDbEntity
import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.repository.AccountRepository

const val lastAccountKey = "LAST_ACCOUNT"

class AccountRepositoryImpl(private val dao : DatabaseDao, private val context: Context) : AccountRepository {
    override suspend fun add(account: Account) {
        dao.addAccount(AccountDbEntity.toDbEntity(account))
    }

    override suspend fun getAccounts(): List<Account?> {
        return dao.getAccounts().map { it?.toAccount() }
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        prefName, Context.MODE_PRIVATE
    )
    private val editor = sharedPreferences.edit()

    override fun getLastAccount(): Account {
        val name = sharedPreferences.getString(lastAccountKey, "Main Portfolio")
        return Account(name!!)
    }

    override fun saveLastAccount(account: Account) {
        editor.putString(lastAccountKey, account.name)
        editor.apply()
    }
}