package com.maksk993.cryptoportfolio.data.models.room

import android.content.Context
import androidx.room.Room
import com.maksk993.cryptoportfolio.data.repository.PortfolioRepositoryImpl

object Database {
    private lateinit var context : Context

    fun init(context: Context){
        Database.context = context
    }

    private val dataBase : AppDataBase by lazy {
        Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .build()
    }

    val dbRepository : PortfolioRepositoryImpl by lazy {
        PortfolioRepositoryImpl(dataBase.getDao())
    }
}