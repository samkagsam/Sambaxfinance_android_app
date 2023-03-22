package com.sambaxfinance.sambax.models

data class RequestModel(
    val phone_number: Int,
    val password: String,
    val first_name: String,
    val last_name: String,
    val customer_image_url: String,
    val customer_id_url: String
)
