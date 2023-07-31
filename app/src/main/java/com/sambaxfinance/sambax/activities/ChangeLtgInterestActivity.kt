package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceChangeLtgInterestRate
import com.sambaxfinance.sambax.api.ApiInterfaceChangeWeeklyGroupContribution
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.ChangeWeeklyContributionRequestModel
import com.sambaxfinance.sambax.models.ChangeWeeklyContributionResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeLtgInterestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_ltg_interest)

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

        //introduce variables from layout
        val button_change_ltg_interest_rate = findViewById<Button>(R.id.button_change_ltg_interest_rate)
        val deposit_money_given = findViewById<EditText>(R.id.et_new_ltg_interest_rate)


        button_change_ltg_interest_rate.setOnClickListener {
            val deposit_money = deposit_money_given.text.toString().toIntOrNull() ?: 0


            if(deposit_money == 0){
                deposit_money_given.error = "interest rate is required"
                deposit_money_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(deposit_money)

            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val changeWeeklyContributionRequestModel = ChangeWeeklyContributionRequestModel(group_identity_int, deposit_money)

            val response = ServiceBuilder.buildService(ApiInterfaceChangeLtgInterestRate::class.java)
            response.sendReq(changeWeeklyContributionRequestModel,"Bearer " + token).enqueue(
                object : Callback<ChangeWeeklyContributionResponseModel> {
                    override fun onResponse(
                        call: Call<ChangeWeeklyContributionResponseModel>,
                        response: Response<ChangeWeeklyContributionResponseModel>
                    ) {
                        Toast.makeText(this@ChangeLtgInterestActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())


                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_new_ltg_interest_rate_response).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@ChangeLtgInterestActivity, LongTermGroupLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@ChangeLtgInterestActivity,"Error in processing deposit", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_new_ltg_interest_rate_response).apply {
                                text = "There was an error in processing your changes"
                            }
                        }
                    }

                    override fun onFailure(call: Call<ChangeWeeklyContributionResponseModel>, t: Throwable) {
                        Toast.makeText(this@ChangeLtgInterestActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_new_ltg_interest_rate_response).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )
        }
    }
}