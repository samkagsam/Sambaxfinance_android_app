package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLongTermGroupWithdraw {
    @POST("user_long_term_group_withdrawal")
    fun sendReq(@Body groupWithdrawRequestModel: GroupWithdrawRequestModel, @Header("Authorization")  authorization:String) : Call<GroupWithdrawResponseModel>
}