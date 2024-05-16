package com.maksk993.cryptoportfolio.data.models.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.maksk993.cryptoportfolio.domain.models.Account

@Entity(tableName = "accounts",
    indices = [
        Index("name", unique = true)
    ]
)
data class AccountDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
) {
    companion object {
        fun toDbEntity(account: Account): AccountDbEntity = AccountDbEntity(id = 0, name = account.name)
    }

    fun toAccount(): Account = Account(name = name)
}