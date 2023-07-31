package com.sambaxfinance.sambax.models

data class LongTermGroupLandingResponseModel(
    val usergroup: String,
    val group_account_balance: String,
    val current_cycle: String,
    val payout_date: String,
    val group_loans: String,
    val group_profit: String,
    val personal_stake: String,
    val group_name: String,
    val loan_balance: String,
    val group_admin: String,
    val interest_rate: String
)
