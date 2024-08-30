package com.sambaxfinance.sambax.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .connectTimeout(180,TimeUnit.SECONDS)
        .writeTimeout(180,TimeUnit.SECONDS)
        .readTimeout(180,TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://sambaxapi.com/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}