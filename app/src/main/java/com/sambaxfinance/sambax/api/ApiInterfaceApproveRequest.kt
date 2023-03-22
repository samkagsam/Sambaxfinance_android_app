package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ApproveRequestModel
import com.sambaxfinance.sambax.models.ApproveResponseModel
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceApproveRequest {

    @POST("approve_request")
    fun sendReq(@Body approveRequestModel: ApproveRequestModel, @Header("Authorization")  authorization:String) : Call<ApproveResponseModel>
}