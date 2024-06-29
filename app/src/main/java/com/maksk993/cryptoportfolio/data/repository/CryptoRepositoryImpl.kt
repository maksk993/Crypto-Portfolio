package com.maksk993.cryptoportfolio.data.repository

import android.util.Log
import com.maksk993.cryptoportfolio.BuildConfig
import com.maksk993.cryptoportfolio.data.models.retrofit.CryptoServiceAPI
import com.maksk993.cryptoportfolio.domain.models.Asset
import com.maksk993.cryptoportfolio.domain.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CryptoRepositoryImpl : CryptoRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pro-api.coinmarketcap.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiKey: String = BuildConfig.API_KEY
    private val service: CryptoServiceAPI = retrofit.create(CryptoServiceAPI::class.java)

    companion object {
        val actualPrices: MutableMap<String, Float> = HashMap()
    }

    override suspend fun getPrices(): Flow<Asset> {
        return flow {
            service.getPrices(apiKey).data.forEach { assetJson ->
                val asset = Asset(
                    assetJson.symbol,
                    assetJson.quote.usd.price
                )
                emit(asset)
                actualPrices[asset.symbol] = asset.price
            }
        }
            .flowOn(Dispatchers.IO)
            .catch { Log.d("PRICES", "FAIL") }
    }
}