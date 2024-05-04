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
import com.sambaxfinance.sambax.api.ApiInterfaceLtgLoanIssue
import com.sambaxfinance.sambax.api.ApiInterfaceLtgQuickLoanEligibility
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LtgLoanIssueRequestModel
import com.sambaxfinance.sambax.models.LtgLoanIssueResponseModel
import com.sambaxfinance.sambax.models.LtgQuickLoanEligibilityResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class LtgLoanIssueActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ltg_loan_issue)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLtgLoanIssueActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout
        val button_take_ltg_loan = findViewById<Button>(R.id.button_take_ltg_loan)
        val loan_money_given = findViewById<EditText>(R.id.et_loan_amount_to_take_from_ltg)

        val groupWithdrawRequestModel = GroupWithdrawRequestModel(group_identity_int)

        val response = ServiceBuilder.buildService(ApiInterfaceLtgQuickLoanEligibility::class.java)
        response.sendReq(groupWithdrawRequestModel,"Bearer " + token).enqueue(
            object : Callback<LtgQuickLoanEligibilityResponseModel> {
                override fun onResponse(
                    call: Call<LtgQuickLoanEligibilityResponseModel>,
                    response: Response<LtgQuickLoanEligibilityResponseModel>
                ) {
                    Toast.makeText(this@LtgLoanIssueActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                        Toast.makeText(this@LtgLoanIssueActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_show_maximum_loan_eligibility).apply {
                            text = "There was an error in processing your request"
                        }
                    }
                }

                override fun onFailure(call: Call<LtgQuickLoanEligibilityResponseModel>, t: Throwable) {
                    Toast.makeText(this@LtgLoanIssueActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    println(t.toString())
                    //show error in error field
                    val tvSaveResponse = findViewById<TextView>(R.id.tv_show_maximum_loan_eligibility).apply {
                        text = "Please check your internet connection"
                    }
                }

            }
        )

        button_take_ltg_loan.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_take_ltg_loan.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_take_ltg_loan.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            println("wololo")
            println(group_identity)

            val loan_money = loan_money_given.text.toString().toIntOrNull() ?: 0

            if(loan_money == 0){
                loan_money_given.error = "loan amount is required"
                loan_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(loan_money)


            val ltgLoanIssueRequestModel = LtgLoanIssueRequestModel(group_identity_int, loan_money)

            val response = ServiceBuilder.buildService(ApiInterfaceLtgLoanIssue::class.java)
            response.sendReq(ltgLoanIssueRequestModel,"Bearer " + token).enqueue(
                object : Callback<LtgLoanIssueResponseModel> {
                    override fun onResponse(
                        call: Call<LtgLoanIssueResponseModel>,
                        response: Response<LtgLoanIssueResponseModel>
                    ) {
                        Toast.makeText(this@LtgLoanIssueActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                            val intent = Intent(this@LtgLoanIssueActivity, LongTermGroupLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                            }
                            startActivity(intent)


                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@LtgLoanIssueActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_loan_amount_response).apply {
                                text = "There was an error in processing your request"
                            }
                        }
                    }

                    override fun onFailure(call: Call<LtgLoanIssueResponseModel>, t: Throwable) {
                        Toast.makeText(this@LtgLoanIssueActivity,t.toString(), Toast.LENGTH_LONG).show()
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