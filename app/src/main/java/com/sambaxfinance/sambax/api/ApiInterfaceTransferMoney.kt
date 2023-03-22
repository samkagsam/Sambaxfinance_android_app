package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import com.sambaxfinance.sambax.models.TransferMoneyRequestModel
import com.sambaxfinance.sambax.models.TransferMoneyResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceTransferMoney {
    @POST("user_transfer_money")
    fun sendReq(@Body transferMoneyRequestModel: TransferMoneyRequestModel, @Header("Authorization")  authorization:String) : Call<TransferMoneyResponseModel>
}