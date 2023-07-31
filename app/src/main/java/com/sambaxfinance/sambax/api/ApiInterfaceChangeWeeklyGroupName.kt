package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupNameChangeRequestModel
import com.sambaxfinance.sambax.models.GroupNameChangeResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceChangeWeeklyGroupName {
    @POST("set_new_name_for_weekly_group")
    fun sendReq(@Body groupNameChangeRequestModel: GroupNameChangeRequestModel, @Header("Authorization")  authorization:String) : Call<GroupNameChangeResponseModel>

}