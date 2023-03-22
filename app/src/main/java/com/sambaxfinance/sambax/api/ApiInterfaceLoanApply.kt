package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ApplyRequestModel
import com.sambaxfinance.sambax.models.ApplyResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLoanApply {
    @POST("applications")
    fun sendReq(@Body applyRequestModel: ApplyRequestModel, @Header("Authorization")  authorization:String) : Call<ApplyResponseModel>
}