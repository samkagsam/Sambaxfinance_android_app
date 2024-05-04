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
import com.sambaxfinance.sambax.api.ApiInterfacePayment
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.PaymentRequestModel
import com.sambaxfinance.sambax.models.PaymentResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class PayLoanActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_loan)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)


        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewPayLoanActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //introduce variables from layout
        val buttonPayLoan = findViewById<Button>(R.id.buttonPayLoan)
        val payment_money_given = findViewById<EditText>(R.id.etPayLoan)

        buttonPayLoan.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonPayLoan.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonPayLoan.isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            val payment_money = payment_money_given.text.toString().toIntOrNull() ?: 0

            if(payment_money == 0){
                payment_money_given.error = "Amount to pay is required"
                payment_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(payment_money)

            val paymentRequestModel = PaymentRequestModel(payment_money)

            val response = ServiceBuilder.buildService(ApiInterfacePayment::class.java)
            response.sendReq(paymentRequestModel,"Bearer " + token).enqueue(
                object : Callback<PaymentResponseModel> {
                    override fun onResponse(
                        call: Call<PaymentResponseModel>,
                        response: Response<PaymentResponseModel>
                    ) {
                        Toast.makeText(this@PayLoanActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvPayResponse = findViewById<TextView>(R.id.tvPayResponse).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@PayLoanActivity, LoansActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@PayLoanActivity,"You have no active loan to pay", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvPayResponse = findViewById<TextView>(R.id.tvPayResponse).apply {
                                text = response.message().toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<PaymentResponseModel>, t: Throwable) {
                        Toast.makeText(this@PayLoanActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvPayResponse = findViewById<TextView>(R.id.tvPayResponse).apply {
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