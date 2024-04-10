package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.CreateLongTermGroupRequestModel
import com.sambaxfinance.sambax.models.ExchangeRequestModel
import com.sambaxfinance.sambax.models.ExchangeResponseModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceCurrencyExchange {
    @POST("currency_exchange")
    fun sendReq(@Body exchangeRequestModel: ExchangeRequestModel, @Header("Authorization")  authorization:String) : Call<ExchangeResponseModel>
}