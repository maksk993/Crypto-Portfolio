package com.maksk993.cryptoportfolio.data.models.retrofit

import com.google.gson.annotations.SerializedName

data class AssetDataJson(
    val data: List<AssetJson>
) {
    data class AssetJson(
        val symbol: String,
        val quote: QuoteJson
    )

    data class QuoteJson(
        @SerializedName("USD") val usd: USDJson
    )

    data class USDJson(
        val price: Float
    )
}
