package com.sambaxfinance.sambax.models

data class TransferMoneyResponseModel(
    val id: Int,
    val amount: Int,
    val transaction_type: String,
    val created_at: String

)
