package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LoginResponseModel
import com.sambaxfinance.sambax.models.UserCreateRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Header


// Get the Intent that started this activity and extract the string
//val signtoken = intent.getStringExtra(EXTRA_MESSAGE)
//println(signtoken)


interface ApiInterfaceUserCreate {

    @POST("users")
    fun sendReq(@Body userCreateRequestModel: UserCreateRequestModel, @Header("Authorization")  authorization:String) : Call<LoginResponseModel>

}