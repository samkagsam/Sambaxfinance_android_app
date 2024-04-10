package com.sambaxfinance.sambax.models

data class AddMoneyByCardRequestModel(
    val card_holder: String,
    val card_number: String,
    val cvv: String,
    val expiry_date: String,
    val amount: Int

)
