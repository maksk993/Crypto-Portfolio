package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository

class RemoveAssetFromPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(asset : AssetItem){
        repository.removeAsset(asset = asset)
    }
}