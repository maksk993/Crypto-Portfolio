package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem

interface PortfolioRepository {
    suspend fun addAsset(asset : PortfolioAssetItem)
    suspend fun removeAsset(asset : AssetItem)
    suspend fun getAllAssetsFromPortfolio(): MutableList<PortfolioAssetItem?>
}