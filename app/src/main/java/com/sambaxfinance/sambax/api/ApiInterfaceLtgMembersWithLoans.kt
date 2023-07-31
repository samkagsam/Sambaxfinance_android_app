package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.GroupMembersRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupMembersResponseModel
import com.sambaxfinance.sambax.models.LtgMembersWithLoansResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterfaceLtgMembersWithLoans {
    @POST("long_term_group_members_with_loans")
    fun sendReq(@Body groupMembersRequestModel: GroupMembersRequestModel, @Header("Authorization")  authorization:String) : Call<List<LtgMembersWithLoansResponseModel>>
}