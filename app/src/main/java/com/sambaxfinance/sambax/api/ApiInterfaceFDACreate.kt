package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import com.sambaxfinance.sambax.models.GroupCreateRequestModel
import com.sambaxfinance.sambax.models.GroupCreateResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceFDACreate {
    @POST("create_fixed_deposit_account")
    fun sendReq(@Body fdaCreateRequestModel: FDACreateRequestModel, @Header("Authorization")  authorization:String) : Call<FDACreateResponseModel>
}