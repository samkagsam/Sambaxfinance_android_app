package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.FdaQuickLoanEligibilityResponseModel
import com.sambaxfinance.sambax.models.FixedAccountLandingResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceFdaQuickLoanEligibility {
    @GET("quick_loan_eligibility_for_fixed_deposit_account")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<FdaQuickLoanEligibilityResponseModel>
}