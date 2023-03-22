package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LoginResponseModel
import com.sambaxfinance.sambax.models.UserCreateRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceOtpRecover {
    @POST("otp_validate")
    fun sendReq(@Body userCreateRequestModel:UserCreateRequestModel, @Header("Authorization")  authorization:String) : Call<LoginResponseModel>
}