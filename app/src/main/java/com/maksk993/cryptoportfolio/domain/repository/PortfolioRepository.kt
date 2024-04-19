package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun addAsset(asset : AssetItem, amount : Float)
    suspend fun getAllAssetsFromPortfolio(): MutableList<PortfolioAssetItem?>
}