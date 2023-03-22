package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.StatementResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceFDAStatement {
    @GET("fixed_deposit_transaction_statement")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<StatementResponseModel>>
}