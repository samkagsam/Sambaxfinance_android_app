package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.AddGroupMembersRequestModel
import com.sambaxfinance.sambax.models.AddGroupMembersResponseModel
import com.sambaxfinance.sambax.models.RemoveGroupMemberResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceRemoveWeeklyGroupMember {
    @POST("remove_weekly_group_member")
    fun sendReq(@Body addGroupMembersRequestModel: AddGroupMembersRequestModel, @Header("Authorization")  authorization:String) : Call<RemoveGroupMemberResponseModel>
}