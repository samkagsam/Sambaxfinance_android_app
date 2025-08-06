package com.sambaxfinance.sambax.models

data class LoanStatementResponseModel(
    val loan_specifics: LoanSpecifics,
    val payments: List<Payment>
)

data class LoanSpecifics(
    val name: String,
    val created_at: String,
    val loan_balance: Int,
    val expiry_date: String,
    val principle: Int,
    val interest: Int,
    val interest_rate: Float,
    val print_date: String
)

data class Payment(
    val id: Int,
    val amount: Int,
    val created_at: String,
    val old_balance: Int,
    val new_balance: Int,
    val transaction_type: String,
    val made_by: String,
    val description: String,
    val old_installment_balance: Int,
    val new_installment_balance: Int,
    val old_arrears_balance: Int,
    val new_arrears_balance: Int
)