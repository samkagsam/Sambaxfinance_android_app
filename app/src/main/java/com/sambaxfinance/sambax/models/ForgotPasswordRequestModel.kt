package com.sambaxfinance.sambax.models

data class ForgotPasswordRequestModel(
    val phone_number: String,
    val email: String
)
