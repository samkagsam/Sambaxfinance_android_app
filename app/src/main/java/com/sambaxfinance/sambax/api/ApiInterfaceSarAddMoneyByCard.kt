package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.AddMoneyByCardRequestModel
import com.sambaxfinance.sambax.models.AddMoneyByCardResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceSarAddMoneyByCard {
    @POST("sar_add_money_by_card")
    fun sendReq(@Body addMoneyByCardRequestModel: AddMoneyByCardRequestModel, @Header("Authorization")  authorization:String) : Call<AddMoneyByCardResponseModel>
}