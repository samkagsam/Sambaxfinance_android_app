package com.sambaxfinance.sambax.models

data class LongTermGroupStatementResponseModel(
    val amount: String,
    val old_balance: String,
    val new_balance: String,
    val transaction_type: String,
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val created_at: String
)
