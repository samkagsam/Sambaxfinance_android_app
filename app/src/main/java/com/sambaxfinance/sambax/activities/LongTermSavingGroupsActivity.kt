package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGeneralGroupLanding
import com.sambaxfinance.sambax.api.ApiInterfaceSeeLongTermGroups
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel
import com.sambaxfinance.sambax.models.MyAdapterUserGroups
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermSavingGroupsActivity : AppCompatActivity() {

    lateinit var myAdapterUserGroups: MyAdapterUserGroups

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_saving_groups)

        //let us initialize more variables
        val recyclerview_user_groups = findViewById<RecyclerView>(R.id.rvUserLongTermSavingGroups)
        recyclerview_user_groups.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_user_groups.layoutManager = linearLayoutManager

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        val response2 = ServiceBuilder.buildService(ApiInterfaceSeeLongTermGroups::class.java)
        response2.sendReq("Bearer " + token).enqueue(
            object : Callback<List<GeneralGroupLandingResponseModel>> {
                override fun onResponse(
                    call: Call<List<GeneralGroupLandingResponseModel>>,
                    response2: Response<List<GeneralGroupLandingResponseModel>>
                ) {


                    Toast.makeText(this@LongTermSavingGroupsActivity,response2.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response2.message().toString())
                    println(response2.body().toString())

                    val response_message_string = response2.message().toString()

                    if (response_message_string == "Forbidden"){
                        println("not allowed")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.akunaLongTermSavingGroup).apply {
                            text = "Oops! There was an error!"
                        }
                    } else{
                        //let us try to show results in recyclerview
                        val responseBody = response2.body()!!
                        println(responseBody)

                        if (responseBody.isEmpty()){
                            println("List is empty")
                            // Capture the layout's TextView and set the string as its text
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.akunaLongTermSavingGroup).apply {
                                text = "Oops! You do not belong to any long term saving group!"
                            }
                        } else{
                            myAdapterUserGroups = MyAdapterUserGroups(baseContext, responseBody)
                            myAdapterUserGroups.notifyDataSetChanged()
                            recyclerview_user_groups.adapter = myAdapterUserGroups

                            myAdapterUserGroups.onItemClick = {
                                val intent = Intent(this@LongTermSavingGroupsActivity, LongTermGroupLandingActivity:: class.java)
                                intent.putExtra("group_credentials", it)
                                intent.putExtra(EXTRA_MESSAGE, token)
                                startActivity(intent)
                            }
                        }
                    }



                }

                override fun onFailure(call: Call<List<GeneralGroupLandingResponseModel>>, t: Throwable) {
                    Toast.makeText(this@LongTermSavingGroupsActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )
    }
}