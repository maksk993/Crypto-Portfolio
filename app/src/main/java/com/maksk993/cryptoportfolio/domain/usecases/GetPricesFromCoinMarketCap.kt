package com.maksk993.cryptoportfolio.domain.usecases

import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow

class GetPricesFromCoinMarketCap(private val repository: CryptoRepository) {
    suspend fun execute(): Flow<Asset> = repository.getPrices()
}