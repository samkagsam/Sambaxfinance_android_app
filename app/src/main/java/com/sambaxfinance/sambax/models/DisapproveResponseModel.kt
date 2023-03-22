package com.sambaxfinance.sambax.models

data class DisapproveResponseModel(
    val id:Int,
    val group: Int,
    val approval_status: String,
    val approval_count: Int
)
