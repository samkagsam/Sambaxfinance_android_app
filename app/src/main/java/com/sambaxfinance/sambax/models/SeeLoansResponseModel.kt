package com.sambaxfinance.sambax.models

data class SeeLoansResponseModel(
    val loan_id: Int,
    val first_name: String,
    val last_name: String,
    val phone_number: Int,
    val loan_balance: Int,
    val installment_balance: Int,
    val arrears: Int,
    val installment_day: Int,
    val expiry_date: String,
    val create_date: String,
    val installment_amount: Int
)
