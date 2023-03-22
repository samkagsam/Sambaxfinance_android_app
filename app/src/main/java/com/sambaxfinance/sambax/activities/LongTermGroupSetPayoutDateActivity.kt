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
import com.sambaxfinance.sambax.api.ApiInterfaceLTGSetPayoutDate
import com.sambaxfinance.sambax.api.ApiInterfaceResetPayoutDate
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import com.sambaxfinance.sambax.models.UpdateLTGPayoutRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermGroupSetPayoutDateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_group_set_payout_date)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()

        //introduce variables from layout
        val button_reset_ltg_payout = findViewById<Button>(R.id.button_reset_ltg_payout)
        val payout_months_given = findViewById<EditText>(R.id.et_reset_ltg_months)


        button_reset_ltg_payout.setOnClickListener {
            val payout_months = payout_months_given.text.toString().toIntOrNull() ?: 0


            if (payout_months == 0) {
                payout_months_given.error = "Months to save are required"
                payout_months_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(payout_months)

            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val updateLTGPayoutRequestModel = UpdateLTGPayoutRequestModel(group_identity_int, payout_months)

            val response = ServiceBuilder.buildService(ApiInterfaceLTGSetPayoutDate::class.java)
            response.sendReq(updateLTGPayoutRequestModel, "Bearer " + token).enqueue(
                object : Callback<FDACreateResponseModel> {
                    override fun onResponse(
                        call: Call<FDACreateResponseModel>,
                        response: Response<FDACreateResponseModel>
                    ) {
                        Toast.makeText(
                            this@LongTermGroupSetPayoutDateActivity,
                            response.message().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.id)
                        println(response.body()?.payout_date)

                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "Created") {
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tv_fda_reset_response).apply {
                                    text = ""
                                }

                            //start a new activity here
                            val intent =
                                Intent(this@LongTermGroupSetPayoutDateActivity, LongTermGroupLandingActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)
                                    putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())

                                }
                            startActivity(intent)
                        } else {
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(
                                this@LongTermGroupSetPayoutDateActivity,
                                "Error in resetting payout date",
                                Toast.LENGTH_LONG
                            ).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tv_fda_reset_response).apply {
                                    text = "There was an error in resetting the payout date of your fixed deposit account"
                                }
                        }
                    }

                    override fun onFailure(call: Call<FDACreateResponseModel>, t: Throwable) {
                        Toast.makeText(this@LongTermGroupSetPayoutDateActivity, t.toString(), Toast.LENGTH_LONG)
                            .show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse =
                            findViewById<TextView>(R.id.tv_fda_reset_response).apply {
                                text = "Please check your internet connection"
                            }
                    }

                }
            )
        }
    }
}