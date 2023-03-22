package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceWithdraw {
    @POST("withdrawals")
    fun sendReq(@Body transactionRequestModel: TransactionRequestModel, @Header("Authorization")  authorization:String) : Call<StatementResponseModel>
}