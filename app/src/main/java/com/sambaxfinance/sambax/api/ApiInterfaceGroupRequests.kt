package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel
import com.sambaxfinance.sambax.models.GroupRequestResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceGroupRequests {
    @GET("group_requests")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<GroupRequestResponseModel>>
}