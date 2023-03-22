package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupWithdraw {
    @POST("user_group_withdrawal")
    fun sendReq(@Body groupWithdrawRequestModel:GroupWithdrawRequestModel, @Header("Authorization")  authorization:String) : Call<GroupWithdrawResponseModel>
}