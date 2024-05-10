package com.maksk993.cryptoportfolio.di

import android.content.Context
import com.maksk993.cryptoportfolio.data.models.room.Database
import com.maksk993.cryptoportfolio.data.repository.CryptoRepositoryImpl
import com.maksk993.cryptoportfolio.data.repository.PortfolioRepositoryImpl
import com.maksk993.cryptoportfolio.data.repository.ThemeRepositoryImpl
import com.maksk993.cryptoportfolio.data.repository.TransactionRepositoryImpl
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.repository.ThemeRepository
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Database(context)
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(): CryptoRepository {
        return CryptoRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePortfolioRepository(database: Database): PortfolioRepository {
        return PortfolioRepositoryImpl(database.getDao())
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(database: Database): TransactionRepository {
        return TransactionRepositoryImpl(database.getDao())
    }

    @Provides
    @Singleton
    fun provideThemeRepository(@ApplicationContext context: Context): ThemeRepository{
        return ThemeRepositoryImpl(context)
    }
}