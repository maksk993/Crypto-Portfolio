package com.maksk993.cryptoportfolio.domain.models

data class PortfolioAssetItem(
    val symbol : String,
    val price : Float,
    var amount : Float = 0F,
    val image : Int = 0,
    val account: Account
) {
    fun toAssetItem() : AssetItem {
        return AssetItem(symbol, price, image)
    }
}
