package com.sambaxfinance.sambax.models

data class AddGroupMembersResponseModel(
    val id: Int,
    val week: Int,
    val group: Int,
    val cycle: String,
    val created_at: String
)
