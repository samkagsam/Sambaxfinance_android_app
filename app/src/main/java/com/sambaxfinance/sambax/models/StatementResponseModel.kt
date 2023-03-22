package com.sambaxfinance.sambax.models

data class StatementResponseModel(
    val id: Int,
    val amount: Int,
    val created_at: String,
    val old_balance: Int,
    val new_balance: Int,
    val transaction_type: String,
    val made_by: String
)
