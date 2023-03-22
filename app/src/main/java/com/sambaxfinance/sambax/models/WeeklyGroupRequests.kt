package com.sambaxfinance.sambax.models

import android.widget.TextView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGetTotalGroupRequests
import com.sambaxfinance.sambax.api.ApiInterfaceGetTotalLongTermGroupRequests
import com.sambaxfinance.sambax.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeeklyGroupRequests {

    private var request_total: Int? = null

    fun getTotalWeeklyGroupRequests(token:String){

        val response = ServiceBuilder.buildService(ApiInterfaceGetTotalGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<GetTotalRequestsResponseModel> {
                override fun onResponse(
                    call: Call<GetTotalRequestsResponseModel>,
                    response: Response<GetTotalRequestsResponseModel>
                ) {

                    val okResponse = response.message().toString()

                    request_total = response.body()?.total_requests
                    println(request_total)

                    //return request_total






                }

                override fun onFailure(call: Call<GetTotalRequestsResponseModel>, t: Throwable) {
                    //Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )


        //println(request_total)
        println("eya")

        //return request_total!!.toInt()
        //return 3

    }
}