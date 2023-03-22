package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupLandingRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupLandingResponseModel
import com.sambaxfinance.sambax.models.LongTermGroupTotalDepositsResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceShowTotalDeposits {
    @POST("my_total_deposits_in_long_term_group")
    fun sendReq(@Body groupWithdrawRequestModel: GroupWithdrawRequestModel, @Header("Authorization")  authorization:String) : Call<LongTermGroupTotalDepositsResponseModel>
}