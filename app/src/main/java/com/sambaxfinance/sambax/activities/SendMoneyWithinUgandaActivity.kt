package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceSendMoneyWithinUganda
import com.sambaxfinance.sambax.api.ApiInterfaceWithdraw
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaRequestModel
import com.sambaxfinance.sambax.models.SendMoneyWithinUgandaResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendMoneyWithinUgandaActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler() // Initialize the Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_money_within_uganda)

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

        println(token)

        //introduce variables from layout
        val button_send_mobile_money_within_uganda = findViewById<Button>(R.id.button_send_mobile_money_within_uganda)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumberTab)
        val amount_given = findViewById<EditText>(R.id.etAmountTab)




        button_send_mobile_money_within_uganda.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_send_mobile_money_within_uganda.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_send_mobile_money_within_uganda.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            println("button now disabled")


            val amount_to_send = amount_given.text.toString().toIntOrNull() ?: 0
            val phone_number_fresh = phone_number_given.text.toString().trim()

            if(amount_to_send == 0){
                amount_given.error = "Amount to send is required"
                amount_given.requestFocus()
                return@setOnClickListener


            }
            println(token)
            println(amount_to_send)

            if(phone_number_fresh.isEmpty()){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            val sendMoneyWithinUgandaRequestModel = SendMoneyWithinUgandaRequestModel(phone_number_fresh, amount_to_send)

            val response = ServiceBuilder.buildService(ApiInterfaceSendMoneyWithinUganda::class.java)
            response.sendReq(sendMoneyWithinUgandaRequestModel,"Bearer " + token).enqueue(
                object : Callback<SendMoneyWithinUgandaResponseModel> {
                    override fun onResponse(
                        call: Call<SendMoneyWithinUgandaResponseModel>,
                        response: Response<SendMoneyWithinUgandaResponseModel>
                    ) {
                        Toast.makeText(this@SendMoneyWithinUgandaActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                            val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponseTab).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@SendMoneyWithinUgandaActivity, LandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)



                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@SendMoneyWithinUgandaActivity,"Error in processing withdraw request", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponseTab).apply {
                                text = "There was an error in processing your request"
                            }



                        }
                    }

                    override fun onFailure(call: Call<SendMoneyWithinUgandaResponseModel>, t: Throwable) {
                        Toast.makeText(this@SendMoneyWithinUgandaActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponseTab).apply {
                            text = "Please check your internet connection"
                        }



                    }

                }
            )
        }
    }
}