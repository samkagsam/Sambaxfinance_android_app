package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupStatementRequestModel
import com.sambaxfinance.sambax.models.GroupStatementResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceGroupStatement {
    @POST("group_statement")
    fun sendReq(@Body groupStatementRequestModel: GroupStatementRequestModel, @Header("Authorization")  authorization:String) : Call<List<GroupStatementResponseModel>>
}