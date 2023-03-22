package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceResetPayoutDate {
    @POST("set_new_payout_date_for_fixed_deposit_account")
    fun sendReq(@Body fdaCreateRequestModel: FDACreateRequestModel, @Header("Authorization")  authorization:String) : Call<FDACreateResponseModel>
}