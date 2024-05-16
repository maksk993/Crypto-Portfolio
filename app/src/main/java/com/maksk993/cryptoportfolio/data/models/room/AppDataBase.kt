package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maksk993.cryptoportfolio.data.models.room.entities.AccountDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity
import com.maksk993.cryptoportfolio.data.models.room.entities.TransactionDbEntity

@Database(
    version = 1,
    entities = [
        AssetDbEntity::class,
        TransactionDbEntity::class,
        AccountDbEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao() : DatabaseDao
}