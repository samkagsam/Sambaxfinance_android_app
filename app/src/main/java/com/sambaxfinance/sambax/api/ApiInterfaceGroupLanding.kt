package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupLandingRequestModel
import com.sambaxfinance.sambax.models.GroupLandingResponseModel
import com.sambaxfinance.sambax.models.PaymentRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupLanding {
    @POST("group/group_landing_page")
    fun sendReq(@Body groupLandingRequestModel: GroupLandingRequestModel, @Header("Authorization")  authorization:String) : Call<GroupLandingResponseModel>
}