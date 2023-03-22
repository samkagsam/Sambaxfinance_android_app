package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.PaymentRequestModel
import com.sambaxfinance.sambax.models.PaymentResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfacePayment {
    @POST("payments")
    fun sendReq(@Body paymentRequestModel: PaymentRequestModel, @Header("Authorization")  authorization:String) : Call<PaymentResponseModel>
}