package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.MyLoanApplicationsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceMyLoanApplications {
    @GET("myapplications")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<MyLoanApplicationsResponseModel>>
}