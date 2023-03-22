package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGroupWithdraw
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.GroupWithdrawResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupWithdrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_withdraw)

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


        val groupWithdrawRequestModel = GroupWithdrawRequestModel(group_identity_int)

        val response = ServiceBuilder.buildService(ApiInterfaceGroupWithdraw::class.java)
        response.sendReq(groupWithdrawRequestModel,"Bearer " + token).enqueue(
            object : Callback<GroupWithdrawResponseModel> {
                override fun onResponse(
                    call: Call<GroupWithdrawResponseModel>,
                    response: Response<GroupWithdrawResponseModel>
                ) {
                    Toast.makeText(this@GroupWithdrawActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()
                    println(okResponse)

                    if (okResponse == "Created"){
                        println("hello")
                        //clear error field
                        /*// Capture the layout's TextView and set the string as its text
                        val tvresponse = findViewById<TextView>(R.id.tvGroupWithdrawPositiveResponse).apply {
                            text = "You have successfully withdrawn your weekly payout"
                        }*/
                        //start a new activity here
                        val intent = Intent(this@GroupWithdrawActivity, GroupActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, token)
                            putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                        }
                        startActivity(intent)

                    }else{
                        println("no hello")
                        //show rejection in textview, refocus user to renter credentials
                        // Capture the layout's TextView and set the string as its text
                        //val wrongCredentialsMessage = "Wrong phone number or password"
                        // Capture the layout's TextView and set the string as its text
                        /*val tvResponse = findViewById<TextView>(R.id.tvGroupWithdrawNegativeResponse).apply {
                            text = "Withdrawal not allowed"
                        }

                        //return@setOnClickListener
                        Toast.makeText(this@GroupWithdrawActivity,"Withdrawal not allowed",Toast.LENGTH_LONG).show()
                        */
                        //start a new activity here
                        val intent = Intent(this@GroupWithdrawActivity, FeedBackActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, token)
                            putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                        }
                        startActivity(intent)

                    }


                }

                override fun onFailure(call: Call<GroupWithdrawResponseModel>, t: Throwable) {
                    Toast.makeText(this@GroupWithdrawActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )
    }
}