package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupMembersRequestModel
import com.sambaxfinance.sambax.models.GroupMembersResponseModel
import com.sambaxfinance.sambax.models.LongTermGroupMembersResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLongTermGroupMembers {
    @POST("see_long_term_group_members")
    fun sendReq(@Body groupMembersRequestModel: GroupMembersRequestModel, @Header("Authorization")  authorization:String) : Call<List<LongTermGroupMembersResponseModel>>
}