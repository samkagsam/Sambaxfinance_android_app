package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.LandingPageResponseModel
import com.sambaxfinance.sambax.models.WalletResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterfaceWallet {
    @GET("wallet")
    fun sendReq(@Header("Authorization")  authorization:String) : Call<WalletResponseModel>
}