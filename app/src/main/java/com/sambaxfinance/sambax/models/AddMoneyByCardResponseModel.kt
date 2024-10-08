package com.sambaxfinance.sambax.models

data class AddMoneyByCardResponseModel(
    val id: Int,
    val amount: Int,
    val created_at: String,
    val old_balance: Int,
    val new_balance: Int,
    val transaction_type: String,
    val made_by: String,
    val currency: String,
    val detail: String
)
