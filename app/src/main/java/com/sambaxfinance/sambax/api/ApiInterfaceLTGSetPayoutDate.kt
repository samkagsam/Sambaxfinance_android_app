package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import com.sambaxfinance.sambax.models.UpdateLTGPayoutRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLTGSetPayoutDate {
    @POST("set_new_payout_date_for_long_term_group")
    fun sendReq(@Body updateLTGPayoutRequestModel: UpdateLTGPayoutRequestModel, @Header("Authorization")  authorization:String) : Call<FDACreateResponseModel>
}