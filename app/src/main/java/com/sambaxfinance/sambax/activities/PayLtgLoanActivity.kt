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

class PayLtgLoanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_ltg_loan)

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

        //introduce variables from layout
        val buttonPayLoan = findViewById<Button>(R.id.buttonPayLtgLoan)
        val payment_money_given = findViewById<EditText>(R.id.etPayLtgLoan)

        buttonPayLoan.setOnClickListener {
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
                        Toast.makeText(this@PayLtgLoanActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                            val intent = Intent(this@PayLtgLoanActivity, LongTermGroupLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_identity)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@PayLtgLoanActivity,"You have no active loan to pay", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvPayResponse = findViewById<TextView>(R.id.tvPayResponse).apply {
                                text = "You have no active loan to pay or the amount you are trying to pay is more than your loan balance"
                            }
                        }
                    }

                    override fun onFailure(call: Call<PaymentResponseModel>, t: Throwable) {
                        Toast.makeText(this@PayLtgLoanActivity,t.toString(), Toast.LENGTH_LONG).show()
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
}