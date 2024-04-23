package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem

class AddAssetToPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(asset : PortfolioAssetItem){
        repository.addAsset(asset = asset)
    }
}