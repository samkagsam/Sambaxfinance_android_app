package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LandingPageResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceLandingPage {
    @GET("landing_page")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<LandingPageResponseModel>
}