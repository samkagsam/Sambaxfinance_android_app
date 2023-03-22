package com.sambaxfinance.sambax.models

data class PaymentResponseModel(
    val id: Int,
    val amount: Int,
    val created_at: String
)
