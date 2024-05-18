package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Account
import com.maksk993.cryptoportfolio.domain.models.Asset

interface PortfolioRepository {
    suspend fun addAsset(asset : Asset)
    suspend fun removeAsset(asset : Asset, account: Account)
    suspend fun getAllAssetsFromPortfolio(account: Account): List<Asset?>
}