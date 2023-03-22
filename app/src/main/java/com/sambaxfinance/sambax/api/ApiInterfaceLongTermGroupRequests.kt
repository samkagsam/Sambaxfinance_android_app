package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupRequestResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceLongTermGroupRequests {
    @GET("long_term_group_requests")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<GroupRequestResponseModel>>
}