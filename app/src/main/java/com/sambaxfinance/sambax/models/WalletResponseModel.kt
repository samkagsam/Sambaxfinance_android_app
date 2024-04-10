package com.sambaxfinance.sambax.models

data class WalletResponseModel(
    val name: String,
    val ugx_balance: String,
    val usd_balance: String,
    val sar_balance: String,
    val qar_balance: String,
    val aed_balance: String
)
