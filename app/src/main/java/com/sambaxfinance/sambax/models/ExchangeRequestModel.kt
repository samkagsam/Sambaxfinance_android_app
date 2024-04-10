package com.sambaxfinance.sambax.models

data class ExchangeRequestModel(
    val from_currency: String,
    val to_currency: String,
    val amount: Int
)
