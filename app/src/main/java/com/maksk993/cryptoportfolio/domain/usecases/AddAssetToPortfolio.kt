package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.models.Asset

class AddAssetToPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(asset : Asset){
        repository.addAsset(asset = asset)
    }
}