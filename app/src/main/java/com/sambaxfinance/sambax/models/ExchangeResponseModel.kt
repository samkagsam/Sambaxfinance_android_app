package com.sambaxfinance.sambax.models

data class ExchangeResponseModel(
    val from_currency: String,
    val amount: Int,
    val to_currency: String,
    val converted_amount: Int
)
