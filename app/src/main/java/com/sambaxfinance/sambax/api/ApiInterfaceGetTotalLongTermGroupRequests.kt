package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GetTotalRequestsResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceGetTotalLongTermGroupRequests {
    @GET("get_total_long_term_group_requests")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<GetTotalRequestsResponseModel>
}