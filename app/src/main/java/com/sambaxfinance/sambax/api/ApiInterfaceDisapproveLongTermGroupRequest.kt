package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.DisapproveRequestModel
import com.sambaxfinance.sambax.models.DisapproveResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceDisapproveLongTermGroupRequest {
    @POST("disapprove_long_term_group_request")
    fun sendReq(@Body disapproveRequestModel: DisapproveRequestModel, @Header("Authorization")  authorization:String) : Call<DisapproveResponseModel>
}