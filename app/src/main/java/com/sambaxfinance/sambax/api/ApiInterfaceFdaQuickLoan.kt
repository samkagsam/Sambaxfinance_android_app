package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FdaQuickLoanRequestModel
import com.sambaxfinance.sambax.models.FdaQuickLoanResponseModel
import com.sambaxfinance.sambax.models.FixedAccountDepositRequestModel
import com.sambaxfinance.sambax.models.FixedAccountDepositResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceFdaQuickLoan {
    @POST("get_quicK_loan_from_fixed_deposit_account")
    fun sendReq(@Body fdaQuickLoanRequestModel: FdaQuickLoanRequestModel, @Header("Authorization")  authorization:String) : Call<FdaQuickLoanResponseModel>
}