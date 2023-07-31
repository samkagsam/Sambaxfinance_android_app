package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.ChangeWeeklyContributionRequestModel
import com.sambaxfinance.sambax.models.ChangeWeeklyContributionResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceChangeLtgInterestRate {
    @POST("set_new_interest_rate_for_long_term_group")
    fun sendReq(@Body changeWeeklyContributionRequestModel: ChangeWeeklyContributionRequestModel, @Header("Authorization")  authorization:String) : Call<ChangeWeeklyContributionResponseModel>
}