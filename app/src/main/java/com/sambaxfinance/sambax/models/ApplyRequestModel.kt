package com.sambaxfinance.sambax.models

data class ApplyRequestModel(
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val email: String,
    val amount: Int,
    val location: String,
    val district: String,
    val country: String,

)
