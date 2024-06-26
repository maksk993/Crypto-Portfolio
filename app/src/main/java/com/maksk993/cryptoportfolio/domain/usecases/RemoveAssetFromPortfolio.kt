package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.domain.repository.PortfolioRepository

class RemoveAssetFromPortfolio(private val repository: PortfolioRepository) {
    suspend fun execute(asset : Asset, account: Account){
        repository.removeAsset(asset = asset, account = account)
    }
}