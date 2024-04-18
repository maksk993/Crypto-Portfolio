package com.maksk993.cryptoportfolio.data.models.room.entities

data class GetAssetsFromDbTuple(
    val id : Long,
    val symbol : String,
    val price : Float,
    val amount : Float
)

data class UpdateAmountTuple(
    val id : Long,
    val amount : Float
)