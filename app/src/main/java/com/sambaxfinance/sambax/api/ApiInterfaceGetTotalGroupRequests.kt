package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GetTotalRequestsResponseModel
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceGetTotalGroupRequests {
    @GET("get_total_weekly_group_requests")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<GetTotalRequestsResponseModel>
}