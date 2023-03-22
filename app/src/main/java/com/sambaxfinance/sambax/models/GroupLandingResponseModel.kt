package com.sambaxfinance.sambax.models

data class GroupLandingResponseModel(
    val usergroup: String,
    val group_payout: String,
    val group_account_balance: String,
    val current_week: String,
    val current_cycle: String,
    val week_beneficiary: String

)
