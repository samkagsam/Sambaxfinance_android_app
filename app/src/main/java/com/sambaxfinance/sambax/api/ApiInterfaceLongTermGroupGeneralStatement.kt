package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupStatementRequestModel
import com.sambaxfinance.sambax.models.GroupStatementResponseModel
import com.sambaxfinance.sambax.models.LongTermGroupStatementResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLongTermGroupGeneralStatement {
    @POST("long_term_group_general_statement")
    fun sendReq(@Body groupStatementRequestModel: GroupStatementRequestModel, @Header("Authorization")  authorization:String) : Call<List<LongTermGroupStatementResponseModel>>
}