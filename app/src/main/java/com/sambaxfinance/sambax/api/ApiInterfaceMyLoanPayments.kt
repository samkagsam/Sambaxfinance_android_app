package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.MyLoanPaymentsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceMyLoanPayments {
    @GET("myloanstatement")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<MyLoanPaymentsResponseModel>>
}