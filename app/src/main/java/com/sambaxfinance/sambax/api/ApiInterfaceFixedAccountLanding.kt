package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FixedAccountLandingResponseModel
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceFixedAccountLanding {
    @GET("fixed_deposit_landing")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<FixedAccountLandingResponseModel>
}