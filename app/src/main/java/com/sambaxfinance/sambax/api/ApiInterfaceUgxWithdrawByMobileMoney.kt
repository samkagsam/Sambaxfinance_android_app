package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceUgxWithdrawByMobileMoney {
    @POST("ugx_withdraw_by_mobile_money")
    fun sendReq(@Body transactionRequestModel: TransactionRequestModel, @Header("Authorization")  authorization:String) : Call<StatementResponseModel>
}