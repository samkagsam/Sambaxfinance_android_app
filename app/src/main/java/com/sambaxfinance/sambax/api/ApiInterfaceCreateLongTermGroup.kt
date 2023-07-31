package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.CreateLongTermGroupRequestModel
import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceCreateLongTermGroup {
    @POST("long_term_groups")
    fun sendReq(@Body createLongTermGroupRequestModel: CreateLongTermGroupRequestModel, @Header("Authorization")  authorization:String) : Call<FDACreateResponseModel>
}