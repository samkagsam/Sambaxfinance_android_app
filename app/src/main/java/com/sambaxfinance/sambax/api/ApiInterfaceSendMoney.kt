package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.SendMoneyRequestModel
import com.sambaxfinance.sambax.models.SendMoneyResponseModel
import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaRequestModel
import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceSendMoney {
    @POST("send_money")
    fun sendReq(@Body sendMoneyRequestModel: SendMoneyRequestModel, @Header("Authorization")  authorization:String) : Call<SendMoneyResponseModel>
}