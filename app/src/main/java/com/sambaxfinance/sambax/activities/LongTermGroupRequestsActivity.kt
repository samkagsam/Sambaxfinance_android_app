package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermGroupRequestsActivity : AppCompatActivity() {

    lateinit var myAdapterGroupRequests: MyAdapterGroupRequests
    lateinit var linearLayoutManager2: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_group_requests)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        //let us initialize more variables
        val recyclerview_group_requests = findViewById<RecyclerView>(R.id.rvLongTermGroupRequests)
        recyclerview_group_requests.setHasFixedSize(true)

        linearLayoutManager2 = LinearLayoutManager(this)
        recyclerview_group_requests.layoutManager = linearLayoutManager2

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        val response = ServiceBuilder.buildService(ApiInterfaceLongTermGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<List<GroupRequestResponseModel>> {
                override fun onResponse(
                    call: Call<List<GroupRequestResponseModel>>,
                    response: Response<List<GroupRequestResponseModel>>
                ) {
                    Toast.makeText(this@LongTermGroupRequestsActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())

                    val response_message = response.message().toString()

                    if (response_message == "Forbidden"){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvAkunaLongTermGroupRequests).apply {
                            text = "Oops! You have no long term group requests!"
                        }
                    } else{
                        //let us try to show results in recyclerview
                        val responseBody = response.body()!!
                        println(responseBody)

                        if (responseBody.isEmpty()){
                            println("List is empty")
                            // Capture the layout's TextView and set the string as its text
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvAkunaLongTermGroupRequests).apply {
                                text = "Oops! You do not have any long term group requests!"
                            }
                        } else{
                            myAdapterGroupRequests = MyAdapterGroupRequests(baseContext, responseBody)
                            myAdapterGroupRequests.notifyDataSetChanged()
                            recyclerview_group_requests.adapter = myAdapterGroupRequests

                            myAdapterGroupRequests.onItemClick = {

                                /*val intent = Intent(this@GroupRequestsActivity, GroupActivity:: class.java)
                                intent.putExtra("group_credentials", it)
                                intent.putExtra(EXTRA_MESSAGE, token)
                                startActivity(intent)*/

                                val request_id = it.request_id
                                println("kugeza")
                                println(request_id)

                                val approveRequestModel = ApproveRequestModel(request_id.toInt())
                                val response = ServiceBuilder.buildService(
                                    ApiInterfaceApproveLongTermGroupRequest::class.java)
                                response.sendReq(approveRequestModel,"Bearer " + token).enqueue(
                                    object : Callback<ApproveResponseModel> {
                                        override fun onResponse(
                                            call: Call<ApproveResponseModel>,
                                            response: Response<ApproveResponseModel>
                                        ) {
                                            Toast.makeText(this@LongTermGroupRequestsActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                                            println("we were successful")
                                            println(response.message().toString())
                                            println(response.body().toString())


                                            val okResponse = response.message().toString()
                                            if (okResponse == "Created"){
                                                println("hello")


                                                //start a new activity here
                                                val intent = Intent(this@LongTermGroupRequestsActivity, LongTermGroupLandingActivity::class.java).apply {
                                                    putExtra(EXTRA_MESSAGE, token)
                                                    putExtra(EXTRA_MESSAGE_GROUP, it.group_number)
                                                }
                                                startActivity(intent)
                                            }else{
                                                println("no hello")
                                                //show rejection in textview, refocus user to renter credentials
                                                Toast.makeText(this@LongTermGroupRequestsActivity,"there was an error in approving the request", Toast.LENGTH_LONG).show()
                                                // Capture the layout's TextView and set the string as its text

                                            }
                                        }

                                        override fun onFailure(call: Call<ApproveResponseModel>, t: Throwable) {
                                            Toast.makeText(this@LongTermGroupRequestsActivity,"Please check your internet connection", Toast.LENGTH_LONG).show()
                                            println("we failed")
                                            println(t.toString())

                                        }

                                    }
                                )
                            }

                            myAdapterGroupRequests.onItemClick2 = {

                                /*val intent = Intent(this@GroupRequestsActivity, GroupActivity:: class.java)
                                intent.putExtra("group_credentials", it)
                                intent.putExtra(EXTRA_MESSAGE, token)
                                startActivity(intent)*/

                                val request_id = it.request_id
                                println("kyekyo")
                                println(request_id)

                                val disapproveRequestModel = DisapproveRequestModel(request_id.toInt())
                                val response = ServiceBuilder.buildService(
                                    ApiInterfaceDisapproveLongTermGroupRequest::class.java)
                                response.sendReq(disapproveRequestModel,"Bearer " + token).enqueue(
                                    object : Callback<DisapproveResponseModel> {
                                        override fun onResponse(
                                            call: Call<DisapproveResponseModel>,
                                            response: Response<DisapproveResponseModel>
                                        ) {
                                            Toast.makeText(this@LongTermGroupRequestsActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                                            println("we were successful")
                                            println(response.message().toString())
                                            println(response.body().toString())


                                            val okResponse = response.message().toString()
                                            if (okResponse == "Created"){
                                                println("hello")


                                                //start a new activity here
                                                val intent = Intent(this@LongTermGroupRequestsActivity, DisapproveRequestActivity::class.java).apply {
                                                    putExtra(EXTRA_MESSAGE, token)
                                                    putExtra(EXTRA_MESSAGE_GROUP, it.group_number)
                                                }
                                                startActivity(intent)
                                            }else{
                                                println("no hello")
                                                //show rejection in textview, refocus user to renter credentials
                                                Toast.makeText(this@LongTermGroupRequestsActivity,"there was an error in disapproving the request", Toast.LENGTH_LONG).show()
                                                // Capture the layout's TextView and set the string as its text

                                            }
                                        }

                                        override fun onFailure(call: Call<DisapproveResponseModel>, t: Throwable) {
                                            Toast.makeText(this@LongTermGroupRequestsActivity,"Please check your internet connection", Toast.LENGTH_LONG).show()
                                            println("we failed")
                                            println(t.toString())

                                        }

                                    }
                                )
                            }
                        }
                    }


                }

                override fun onFailure(call: Call<List<GroupRequestResponseModel>>, t: Throwable) {
                    Toast.makeText(this@LongTermGroupRequestsActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )
    }
}