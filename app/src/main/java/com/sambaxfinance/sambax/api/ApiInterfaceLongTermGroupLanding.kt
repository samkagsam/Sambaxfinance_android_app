package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupLandingRequestModel
import com.sambaxfinance.sambax.models.GroupLandingResponseModel
import com.sambaxfinance.sambax.models.LongTermGroupLandingResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLongTermGroupLanding {
    @POST("specific_long_term_saving_group_landing")
    fun sendReq(@Body groupLandingRequestModel: GroupLandingRequestModel, @Header("Authorization")  authorization:String) : Call<LongTermGroupLandingResponseModel>
}