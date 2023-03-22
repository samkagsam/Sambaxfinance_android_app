package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupStatementRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupStatementResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfacePersonalGroupStatement {
    @POST("my_personal_statement_in_long_term_group")
    fun sendReq(@Body groupStatementRequestModel: GroupStatementRequestModel, @Header("Authorization")  authorization:String) : Call<List<LongTermGroupStatementResponseModel>>
}