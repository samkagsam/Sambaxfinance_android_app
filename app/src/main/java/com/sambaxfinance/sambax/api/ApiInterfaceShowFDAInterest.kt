package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FixedAccountInterestResponseModel
import com.sambaxfinance.sambax.models.FixedAccountWithdrawResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceShowFDAInterest {
    @GET("get_fixed_deposit_interest")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<FixedAccountInterestResponseModel>
}