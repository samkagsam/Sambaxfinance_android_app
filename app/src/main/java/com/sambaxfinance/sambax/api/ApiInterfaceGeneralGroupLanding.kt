package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel
import com.sambaxfinance.sambax.models.GroupLandingResponseModel
import com.sambaxfinance.sambax.models.GroupMembersResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceGeneralGroupLanding {
    @GET("groups_user_belongs_to")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<GeneralGroupLandingResponseModel>>
}