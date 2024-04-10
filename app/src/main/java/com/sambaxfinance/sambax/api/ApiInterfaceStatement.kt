package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.StatementResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceStatement {
    @GET("transaction_history")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<StatementResponseModel>>
}