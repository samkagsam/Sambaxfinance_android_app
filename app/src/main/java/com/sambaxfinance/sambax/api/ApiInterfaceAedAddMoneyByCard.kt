package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.AddMoneyByCardRequestModel
import com.sambaxfinance.sambax.models.AddMoneyByCardResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceAedAddMoneyByCard {
    @POST("aed_add_money_by_card")
    fun sendReq(@Body addMoneyByCardRequestModel: AddMoneyByCardRequestModel, @Header("Authorization")  authorization:String) : Call<AddMoneyByCardResponseModel>
}