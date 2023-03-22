package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceShowLTGInterest
import com.sambaxfinance.sambax.api.ApiInterfaceShowTotalDeposits
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FixedAccountInterestResponseModel
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupTotalDepositsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInterestInLongTermGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_interest_in_long_term_group)

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

        val groupWithdrawRequestModel = GroupWithdrawRequestModel(group_identity_int)

        val response = ServiceBuilder.buildService(ApiInterfaceShowLTGInterest::class.java)
        response.sendReq(groupWithdrawRequestModel,"Bearer " + token).enqueue(
            object : Callback<FixedAccountInterestResponseModel> {
                override fun onResponse(
                    call: Call<FixedAccountInterestResponseModel>,
                    response: Response<FixedAccountInterestResponseModel>
                ) {
                    Toast.makeText(this@MyInterestInLongTermGroupActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())


                    val okResponse = response.message().toString()
                    if (okResponse == "OK"){
                        println("hello")
                        //let us first clear all error codes
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tvShowTotalDeposits).apply {
                            text = "UgX "+ response.body()?.total_interest
                        }


                    }else{
                        println("no hello")
                        //show rejection in textview, refocus user to renter credentials
                        Toast.makeText(this@MyInterestInLongTermGroupActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tvNoDeposits).apply {
                            text = "There was an error in processing your request"
                        }
                    }
                }

                override fun onFailure(call: Call<FixedAccountInterestResponseModel>, t: Throwable) {
                    Toast.makeText(this@MyInterestInLongTermGroupActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    println(t.toString())
                    //show error in error field
                    val tvSaveResponse = findViewById<TextView>(R.id.tvNoDeposits).apply {
                        text = "Please check your internet connection"
                    }
                }

            }
        )
    }
}