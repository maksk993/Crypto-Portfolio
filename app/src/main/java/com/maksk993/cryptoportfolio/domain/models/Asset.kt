package com.maksk993.cryptoportfolio.domain.models

data class Asset(
    val symbol : String,
    var price : Float = -1F,
    var amount : Float = 0F,
    val image : Int = 0,
    val account: Account? = null
)