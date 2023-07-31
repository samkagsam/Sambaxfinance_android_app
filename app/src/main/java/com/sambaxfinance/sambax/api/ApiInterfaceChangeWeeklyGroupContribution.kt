package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ChangeWeeklyContributionRequestModel
import com.sambaxfinance.sambax.models.ChangeWeeklyContributionResponseModel
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceChangeWeeklyGroupContribution {
    @POST("change_member_contribution_of_weekly_group")
    fun sendReq(@Body changeWeeklyContributionRequestModel: ChangeWeeklyContributionRequestModel, @Header("Authorization")  authorization:String) : Call<ChangeWeeklyContributionResponseModel>
}