package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository
import com.maksk993.cryptoportfolio.domain.models.AssetItem

class AddAssetToPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(asset : AssetItem, amount : Float){
        repository.addAsset(asset = asset, amount = amount)
    }
}