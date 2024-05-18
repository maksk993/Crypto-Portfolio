package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository

class GetAssetsFromPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(account: Account) : List<Asset?>{
        return repository.getAllAssetsFromPortfolio(account)
    }
}