package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LoginResponseModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterfaceLogin {
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("login")
    fun sendReq(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<LoginResponseModel>
}