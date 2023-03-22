package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceSeeLongTermGroups {
    @GET("long_term_groups_user_belongs_to")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<List<GeneralGroupLandingResponseModel>>
}