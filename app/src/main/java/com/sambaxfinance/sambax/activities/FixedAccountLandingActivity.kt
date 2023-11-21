package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceFixedAccountLanding
import com.sambaxfinance.sambax.api.ApiInterfaceFixedAccountWithdraw
import com.sambaxfinance.sambax.api.ApiInterfaceLandingPage
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FixedAccountLandingResponseModel
import com.sambaxfinance.sambax.models.FixedAccountWithdrawResponseModel
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixedAccountLandingActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_account_landing)

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

        //let us initiate all the buttons
        val button_fd_deposit = findViewById<Button>(R.id.button_fd_deposit)
        val button_fd_withdraw = findViewById<Button>(R.id.button_fd_withdraw)
        val button_fd_statement = findViewById<Button>(R.id.button_fd_statement)
        val button_set_fd_payout = findViewById<Button>(R.id.button_set_fd_payout)
        val button_see_fda_interest = findViewById<Button>(R.id.button_see_fda_interest)
        val button_go_to_fda_quick_loan_note = findViewById<Button>(R.id.button_go_to_fda_quick_loan_note)


        val response = ServiceBuilder.buildService(ApiInterfaceFixedAccountLanding::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<FixedAccountLandingResponseModel> {
                override fun onResponse(
                    call: Call<FixedAccountLandingResponseModel>,
                    response: Response<FixedAccountLandingResponseModel>
                ) {
                    Toast.makeText(this@FixedAccountLandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")

                    val okResponse = response.message().toString()
                    println(okResponse)

                    if (okResponse == "OK"){
                        println("hello")
                        // Capture the layout's TextView and set the string as its text
                        val tv_fd_account_balance = findViewById<TextView>(R.id.tv_fd_account_balance).apply {
                            text = "Account Balance: "+ response.body()?.account_balance
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_fd_payout_date = findViewById<TextView>(R.id.tv_fd_payout_date).apply {
                            text = "Payout Date: "+ response.body()?.payout_date
                        }


                    }else{
                        println("no hello")

                        //start a new activity here
                        val intent = Intent(this@FixedAccountLandingActivity, FixedAccountActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, token)
                        }
                        startActivity(intent)
                    }



                }

                override fun onFailure(call: Call<FixedAccountLandingResponseModel>, t: Throwable) {
                    Toast.makeText(this@FixedAccountLandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )

        button_fd_deposit.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountDepositActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_fd_withdraw.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_fd_withdraw.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_fd_withdraw.isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            //do the withdraw here
            val response = ServiceBuilder.buildService(ApiInterfaceFixedAccountWithdraw::class.java)
            response.sendReq("Bearer " + token).enqueue(
                object : Callback<FixedAccountWithdrawResponseModel> {
                    override fun onResponse(
                        call: Call<FixedAccountWithdrawResponseModel>,
                        response: Response<FixedAccountWithdrawResponseModel>
                    ) {
                        Toast.makeText(this@FixedAccountLandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes


                            //start a new activity here
                            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@FixedAccountLandingActivity,"Error in processing withdraw request", Toast.LENGTH_LONG).show()
                            //start a new activity here
                            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountWithdrawActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }


                    }

                    override fun onFailure(call: Call<FixedAccountWithdrawResponseModel>, t: Throwable) {
                        //Toast.makeText(this@FixedAccountLandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                        //show toast
                        Toast.makeText(this@FixedAccountLandingActivity,"Please check your internet connection", Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())



                    }

                }
            )


        }

        button_fd_statement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountStatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_set_fd_payout.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountSetPayoutDateActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_see_fda_interest.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@FixedAccountLandingActivity, FixedAccountInterestActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_go_to_fda_quick_loan_note.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@FixedAccountLandingActivity, FdaLoanNoteActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
    }
}