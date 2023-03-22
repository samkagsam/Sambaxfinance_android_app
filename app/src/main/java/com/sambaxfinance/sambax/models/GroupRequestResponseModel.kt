package com.sambaxfinance.sambax.models

data class GroupRequestResponseModel(
    val request_id:String,
    val group_number: String,
    val admin_first_name: String,
    val admin_last_name: String,
    val admin_phone_number:String
)
