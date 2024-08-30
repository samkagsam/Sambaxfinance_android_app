package com.sambaxfinance.sambax.models

data class RequestModel(

    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val email: String,
    val password: String,
    val country: String,
    val country_code: String,
    val sex: String

)
