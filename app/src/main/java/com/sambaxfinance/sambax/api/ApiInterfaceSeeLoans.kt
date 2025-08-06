package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupRequestResponseModel
import com.sambaxfinance.sambax.models.LoansResponseModel
import com.sambaxfinance.sambax.models.SeeLoansResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceSeeLoans {
    @GET("my_active_loans")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<SeeLoansResponseModel>>
}