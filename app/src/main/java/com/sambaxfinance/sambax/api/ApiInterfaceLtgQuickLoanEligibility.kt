package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupTotalDepositsResponseModel
import com.sambaxfinance.sambax.models.LtgQuickLoanEligibilityRequestModel
import com.sambaxfinance.sambax.models.LtgQuickLoanEligibilityResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLtgQuickLoanEligibility {
    @POST("quick_loan_eligibility_in_long_term_group")
    fun sendReq(@Body groupWithdrawRequestModel: GroupWithdrawRequestModel, @Header("Authorization")  authorization:String) : Call<LtgQuickLoanEligibilityResponseModel>
}