package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupCreateRequestModel
import com.sambaxfinance.sambax.models.GroupCreateResponseModel
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupCreate {
    @POST("groups")
    fun sendReq(@Body groupCreateRequestModel: GroupCreateRequestModel, @Header("Authorization")  authorization:String) : Call<GroupCreateResponseModel>
}