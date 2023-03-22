package com.sambaxfinance.sambax.api


import com.sambaxfinance.sambax.models.RequestModel
import com.sambaxfinance.sambax.models.ResponseModel
//import com.example.sambax.models.UserCredentials
//import com.example.sambax.models.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("user_signup")
    fun sendReq(@Body requestModel: RequestModel) : Call<ResponseModel>
}
