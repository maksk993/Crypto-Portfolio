package com.maksk993.cryptoportfolio.data.models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maksk993.cryptoportfolio.data.models.room.entities.AssetDbEntity

@Database(
    version = 1,
    entities = [
        AssetDbEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao() : AssetsDbDao
}