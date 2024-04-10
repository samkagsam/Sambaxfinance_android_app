package com.sambaxfinance.sambax.models

data class SendMoneyRequestModel(
    val from_currency: String,
    val amount: Int,
    val receiver_name: String,
    val receiver_phone_number: String,
    val receiver_country: String,
    val receiver_method: String
)
