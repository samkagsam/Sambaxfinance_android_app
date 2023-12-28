package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaRequestModel
import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceSendMoneyWithinUganda {
    @POST("send_money_within_uganda")
    fun sendReq(@Body sendMoneyWithinUgandaRequestModel: SendMoneyWithinUgandaRequestModel, @Header("Authorization")  authorization:String) : Call<SendMoneyWithinUgandaResponseModel>
}