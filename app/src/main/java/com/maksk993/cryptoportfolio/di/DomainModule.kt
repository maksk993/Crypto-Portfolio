package com.maksk993.cryptoportfolio.di

import com.maksk993.cryptoportfolio.domain.repository.AccountRepository
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.repository.ThemeRepository
import com.maksk993.cryptoportfolio.domain.repository.TransactionRepository
import com.maksk993.cryptoportfolio.domain.usecases.AddAccount
import com.maksk993.cryptoportfolio.domain.usecases.AddAssetToPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetAccounts
import com.maksk993.cryptoportfolio.domain.usecases.GetAssetsFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.GetLastAccount
import com.maksk993.cryptoportfolio.domain.usecases.GetPricesFromCoinMarketCap
import com.maksk993.cryptoportfolio.domain.usecases.GetSavedAppTheme
import com.maksk993.cryptoportfolio.domain.usecases.GetTransactions
import com.maksk993.cryptoportfolio.domain.usecases.RemoveAssetFromPortfolio
import com.maksk993.cryptoportfolio.domain.usecases.SaveLastAccount
import com.maksk993.cryptoportfolio.domain.usecases.SaveTransaction
import com.maksk993.cryptoportfolio.domain.usecases.SwitchAppTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetPricesFromCoinMarketCap(cryptoRepository: CryptoRepository): GetPricesFromCoinMarketCap{
        return GetPricesFromCoinMarketCap(cryptoRepository)
    }

    @Provides
    fun provideAddAssetToPortfolio(portfolioRepository: PortfolioRepository): AddAssetToPortfolio{
        return AddAssetToPortfolio(portfolioRepository)
    }

    @Provides
    fun provideRemoveAssetFromPortfolio(portfolioRepository: PortfolioRepository): RemoveAssetFromPortfolio{
        return RemoveAssetFromPortfolio(portfolioRepository)
    }

    @Provides
    fun provideGetAssetsFromPortfolio(portfolioRepository: PortfolioRepository): GetAssetsFromPortfolio{
        return GetAssetsFromPortfolio(portfolioRepository)
    }

    @Provides
    fun provideSaveTransaction(transactionRepository: TransactionRepository): SaveTransaction{
        return SaveTransaction(transactionRepository)
    }

    @Provides
    fun provideGetTransactions(transactionRepository: TransactionRepository): GetTransactions{
        return GetTransactions(transactionRepository)
    }

    @Provides
    fun provideGetSavedAppTheme(themeRepository: ThemeRepository): GetSavedAppTheme {
        return GetSavedAppTheme(themeRepository)
    }

    @Provides
    fun provideSwitchTheme(themeRepository: ThemeRepository): SwitchAppTheme {
        return SwitchAppTheme(themeRepository)
    }

    @Provides
    fun provideAddAccount(accountRepository: AccountRepository): AddAccount{
        return AddAccount(accountRepository)
    }

    @Provides
    fun provideGetAccounts(accountRepository: AccountRepository): GetAccounts {
        return GetAccounts(accountRepository)
    }

    @Provides
    fun provideSaveLastAccount(accountRepository: AccountRepository): SaveLastAccount {
        return SaveLastAccount(accountRepository)
    }

    @Provides
    fun provideGetLastAccount(accountRepository: AccountRepository): GetLastAccount {
        return GetLastAccount(accountRepository)
    }
}