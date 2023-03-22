package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.NewPasswordRequestModel
import com.sambaxfinance.sambax.models.NewPasswordResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceNewPassword {
    @POST("password_update")
    fun sendReq(@Body newPasswordRequestModel: NewPasswordRequestModel, @Header("Authorization")  authorization:String) : Call<NewPasswordResponseModel>
}