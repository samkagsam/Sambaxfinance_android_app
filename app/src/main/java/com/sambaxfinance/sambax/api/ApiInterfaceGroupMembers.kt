package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupLandingRequestModel
import com.sambaxfinance.sambax.models.GroupMembersRequestModel
import com.sambaxfinance.sambax.models.GroupMembersResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupMembers {
    @POST("group/group_members")
    fun sendReq(@Body groupMembersRequestModel: GroupMembersRequestModel, @Header("Authorization")  authorization:String) : Call<List<GroupMembersResponseModel>>
}