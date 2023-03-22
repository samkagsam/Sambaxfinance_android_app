package com.sambaxfinance.sambax.api

import com.sambaxfinance.sambax.models.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyApi {
    @Multipart
    @POST("profile/")
    fun uploadImage(

        @Part picture: MultipartBody.Part,
        @Part("name") name: RequestBody

    ): Call<UploadResponse>

    companion object {
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl("https://sambaxfinance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}