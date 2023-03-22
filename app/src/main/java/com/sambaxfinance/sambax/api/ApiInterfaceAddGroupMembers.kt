package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.AddGroupMembersRequestModel
import com.sambaxfinance.sambax.models.AddGroupMembersResponseModel
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceAddGroupMembers {
    @POST("payees")
    fun sendReq(@Body addGroupMembersRequestModel: AddGroupMembersRequestModel, @Header("Authorization")  authorization:String) : Call<AddGroupMembersResponseModel>
}