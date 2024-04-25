package com.maksk993.cryptoportfolio.domain.models

data class Transaction(val symbol : String, val amount : Float, val transactionPrice : Float, val type : TransactionType)

enum class TransactionType{
    BUY, SELL
}
