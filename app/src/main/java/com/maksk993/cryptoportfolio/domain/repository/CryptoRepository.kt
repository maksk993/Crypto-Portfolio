package com.maksk993.cryptoportfolio.domain.repository

import com.maksk993.cryptoportfolio.domain.models.Asset
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    suspend fun getPrices(): Flow<Asset>
}