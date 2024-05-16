package com.maksk993.cryptoportfolio.data.models.room

import android.content.Context
import androidx.room.Room

class Database(val context: Context) {

    private val dataBase : AppDataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .build()
    }

    fun getDao(): DatabaseDao = dataBase.getDao()
}