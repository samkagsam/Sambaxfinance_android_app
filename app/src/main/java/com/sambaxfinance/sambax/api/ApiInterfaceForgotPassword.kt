package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ForgotPasswordRequestModel
import com.sambaxfinance.sambax.models.ForgotPasswordResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterfaceForgotPassword {
    @POST("forgot_password")
    fun sendReq(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel) : Call<ForgotPasswordResponseModel>
}