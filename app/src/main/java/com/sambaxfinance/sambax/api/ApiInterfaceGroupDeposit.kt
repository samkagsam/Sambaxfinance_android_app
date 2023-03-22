package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupDeposit {
    @POST("user_group_payments")
    fun sendReq(@Body groupDepositRequestModel: GroupDepositRequestModel, @Header("Authorization")  authorization:String) : Call<GroupDepositResponseModel>
}