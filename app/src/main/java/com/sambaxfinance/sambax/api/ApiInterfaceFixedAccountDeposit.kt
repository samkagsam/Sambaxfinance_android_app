package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FixedAccountDepositRequestModel
import com.sambaxfinance.sambax.models.FixedAccountDepositResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceFixedAccountDeposit {
    @POST("fixed_account_deposit_money")
    fun sendReq(@Body fixedAccountDepositRequestModel: FixedAccountDepositRequestModel, @Header("Authorization")  authorization:String) : Call<FixedAccountDepositResponseModel>
}