package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LtgLoanIssueRequestModel
import com.sambaxfinance.sambax.models.LtgLoanIssueResponseModel
import com.sambaxfinance.sambax.models.LtgQuickLoanEligibilityResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLtgLoanIssue {
    @POST("get_quicK_loan_from_long_term_group")
    fun sendReq(@Body ltgLoanIssueRequestModel: LtgLoanIssueRequestModel, @Header("Authorization")  authorization:String) : Call<LtgLoanIssueResponseModel>
}