package com.maksk993.cryptoportfolio.data.models.retrofit

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val SORT_BY = "market_cap"
const val LIMIT = 50

interface CryptoServiceAPI {
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getPrices(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("sort") sortBy: String = SORT_BY,
        @Query("limit") min: Int = LIMIT
    ): AssetDataJson
}