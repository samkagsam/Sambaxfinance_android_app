package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGroupMembers
import com.sambaxfinance.sambax.api.ApiInterfaceLongTermGroupMembers
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermGroupMembersActivity : AppCompatActivity() {

    lateinit var myAdapterGroupMembers: MyAdapterLongTermGroupMembers
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_group_members)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //let us initialize more variables
        val recyclerview_group_members = findViewById<RecyclerView>(R.id.recyclerview_long_term_group_members)
        recyclerview_group_members.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_group_members.layoutManager = linearLayoutManager

        val groupMembersRequestModel = GroupMembersRequestModel(group_identity_int)
        val response2 = ServiceBuilder.buildService(ApiInterfaceLongTermGroupMembers::class.java)
        response2.sendReq(groupMembersRequestModel,"Bearer " + token).enqueue(
            object : Callback<List<LongTermGroupMembersResponseModel>> {
                override fun onResponse(
                    call: Call<List<LongTermGroupMembersResponseModel>>,
                    response2: Response<List<LongTermGroupMembersResponseModel>>
                ) {
                    Toast.makeText(this@LongTermGroupMembersActivity,response2.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response2.message().toString())
                    println(response2.body().toString())
                    //println(response.body()?.get(1))
                    //println(response.body()?.get(0))

                    /*

                    val okResponse = response.message().toString()
                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.tvHello).apply {
                        text = "Hello "+ response.body()?.get(1)
                    }*/

                    //let us try to show results in recyclerview
                    val responseBody = response2.body()!!
                    println(responseBody)

                    if (responseBody.isEmpty()){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroupMembers).apply {
                            text = "Oops! You do not belong to any saving group!"
                        }
                    } else{
                        myAdapterGroupMembers = MyAdapterLongTermGroupMembers(baseContext, responseBody)
                        myAdapterGroupMembers.notifyDataSetChanged()
                        recyclerview_group_members.adapter = myAdapterGroupMembers
                    }


                }

                override fun onFailure(call: Call<List<LongTermGroupMembersResponseModel>>, t: Throwable) {
                    Toast.makeText(this@LongTermGroupMembersActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )
    }
}