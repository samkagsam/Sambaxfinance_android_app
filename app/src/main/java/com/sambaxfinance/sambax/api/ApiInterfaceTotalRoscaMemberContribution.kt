package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceTotalRoscaMemberContribution {
    @POST("total_weekly_contributions_for_each_member")
    fun sendReq(@Body totalRoscaMemberContributionRequestModel: TotalRoscaMemberContributionRequestModel, @Header("Authorization")  authorization:String) : Call<List<TotalRoscaMemberContributionResponseModel>>
}