package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ApproveRequestModel
import com.sambaxfinance.sambax.models.ApproveResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceApproveLongTermGroupRequest {
    @POST("approve_long_term_group_request")
    fun sendReq(@Body approveRequestModel: ApproveRequestModel, @Header("Authorization")  authorization:String) : Call<ApproveResponseModel>
}