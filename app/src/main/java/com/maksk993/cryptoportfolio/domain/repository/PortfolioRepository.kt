package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.AssetItem
import com.maksk993.cryptoportfolio.domain.models.PortfolioAssetItem

interface PortfolioRepository {
    suspend fun addAsset(asset : PortfolioAssetItem)
    suspend fun removeAsset(asset : AssetItem, account: Account)
    suspend fun getAllAssetsFromPortfolio(account: Account): MutableList<PortfolioAssetItem?>
}