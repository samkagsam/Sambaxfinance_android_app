package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LandingPageResponseModel
import com.sambaxfinance.sambax.models.LoansResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceLoans {
    @GET("my_number_of_active_loans")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<LoansResponseModel>
}