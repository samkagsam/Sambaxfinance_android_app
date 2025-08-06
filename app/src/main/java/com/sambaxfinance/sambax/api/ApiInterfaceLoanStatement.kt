package com.sambaxfinance.sambax.api


import com.sambaxfinance.sambax.models.LoanStatementRequestModel
import com.sambaxfinance.sambax.models.LoanStatementResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLoanStatement {
    @POST("user_get_loan_statement")
    fun sendReq(@Body loanStatementRequestModel: LoanStatementRequestModel, @Header("Authorization")  authorization:String) : Call<LoanStatementResponseModel>
}