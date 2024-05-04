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
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class FdaQuickLoanActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler() // Initialize the Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fda_quick_loan)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewFdaQuickLoanActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }


        //val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        //val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout
        val button_take_fda_loan = findViewById<Button>(R.id.button_take_fda_loan)
        val loan_money_given = findViewById<EditText>(R.id.et_loan_amount_to_take_from_fda)



        val response = ServiceBuilder.buildService(ApiInterfaceFdaQuickLoanEligibility::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<FdaQuickLoanEligibilityResponseModel> {
                override fun onResponse(
                    call: Call<FdaQuickLoanEligibilityResponseModel>,
                    response: Response<FdaQuickLoanEligibilityResponseModel>
                ) {
                    Toast.makeText(this@FdaQuickLoanActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())


                    val okResponse = response.message().toString()
                    println(okResponse)

                    if (okResponse == "OK"){
                        println("hello")
                        //let us first clear all error codes
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_show_maximum_loan_eligibility).apply {
                            text = "Enter Loan amount between UgX 1000  to UgX "+ response.body()?.maximum_eligible_loan
                        }


                    }else{
                        println("no hello")
                        //show rejection in textview, refocus user to renter credentials
                        Toast.makeText(this@FdaQuickLoanActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_show_maximum_loan_eligibility).apply {
                            text = "There was an error in processing your request"
                        }
                    }
                }

                override fun onFailure(call: Call<FdaQuickLoanEligibilityResponseModel>, t: Throwable) {
                    Toast.makeText(this@FdaQuickLoanActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    println(t.toString())
                    //show error in error field
                    val tvSaveResponse = findViewById<TextView>(R.id.tv_show_maximum_loan_eligibility).apply {
                        text = "Please check your internet connection"
                    }
                }

            }
        )

        button_take_fda_loan.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_take_fda_loan.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_take_fda_loan.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            // ... (the rest of your button click code)

            println("wololo")
            //println(group_identity)

            val loan_money = loan_money_given.text.toString().toIntOrNull() ?: 0

            if(loan_money == 0){
                loan_money_given.error = "loan amount is required"
                loan_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(loan_money)


            val fdaQuickLoanRequestModel = FdaQuickLoanRequestModel(loan_money)

            val response = ServiceBuilder.buildService(ApiInterfaceFdaQuickLoan::class.java)
            response.sendReq(fdaQuickLoanRequestModel,"Bearer " + token).enqueue(
                object : Callback<FdaQuickLoanResponseModel> {
                    override fun onResponse(
                        call: Call<FdaQuickLoanResponseModel>,
                        response: Response<FdaQuickLoanResponseModel>
                    ) {
                        Toast.makeText(this@FdaQuickLoanActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())


                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "OK"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_loan_amount_response).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@FdaQuickLoanActivity, FixedAccountLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)

                            }
                            startActivity(intent)


                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@FdaQuickLoanActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_loan_amount_response).apply {
                                text = "There was an error in processing your request"
                            }
                        }
                    }

                    override fun onFailure(call: Call<FdaQuickLoanResponseModel>, t: Throwable) {
                        Toast.makeText(this@FdaQuickLoanActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_loan_amount_response).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )


        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        // Handle action bar item clicks here.
        ActionBarUtils.handleActionBarItemClick(this, item, token)

        return super.onOptionsItemSelected(item)

    }
}