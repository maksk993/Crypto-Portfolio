package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository

class GetAssetsFromPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute() : MutableList<PortfolioAssetItem?>{
        return repository.getAllAssetsFromPortfolio()
    }
}