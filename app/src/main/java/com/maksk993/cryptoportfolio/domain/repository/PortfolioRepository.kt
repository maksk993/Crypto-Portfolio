package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.AssetItem
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    fun findAssetById(id : Long)  : Flow<AssetItem?>
    suspend fun updateAmount(id : Long, newAmount : Float)
    suspend fun addNewAsset(asset : AssetItem, amount : Float)
}