package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FixedAccountWithdrawRequestModel
import com.sambaxfinance.sambax.models.FixedAccountWithdrawResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceFixedAccountWithdraw {
    @GET("fixed_account_withdraw_money")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<FixedAccountWithdrawResponseModel>
}