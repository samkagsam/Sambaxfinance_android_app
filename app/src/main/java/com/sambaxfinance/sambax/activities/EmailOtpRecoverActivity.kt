package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceOtpRecover
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.LoginResponseModel
import com.sambaxfinance.sambax.models.UserCreateRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailOtpRecoverActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_otp_recover)

        // Get the Intent that started this activity and extract the string
        val recoverToken = intent.getStringExtra(EMAIL_ADDRESS_RECOVER_TOKEN_CARRIER)
        //println(signtoken)

        //introduce variables from layout
        val buttonSendOtpRecover = findViewById<Button>(R.id.buttonSendOtpEmailRecover)
        val otp_given = findViewById<EditText>(R.id.etOtpEmailRecover)

        buttonSendOtpRecover.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonSendOtpRecover.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonSendOtpRecover.isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            val otp = otp_given.text.toString().toIntOrNull() ?: 0

            if(otp == 0){
                otp_given.error = "otp is required"
                otp_given.requestFocus()
                return@setOnClickListener

            }
            //println(signtoken)
            //println(otp)

            val userCreateRequestModel = UserCreateRequestModel(otp)

            val response = ServiceBuilder.buildService(ApiInterfaceOtpRecover::class.java)
            response.sendReq(userCreateRequestModel,"Bearer " + recoverToken).enqueue(
                object : Callback<LoginResponseModel> {
                    override fun onResponse(
                        call: Call<LoginResponseModel>,
                        response: Response<LoginResponseModel>
                    ) {
                        Toast.makeText(this@EmailOtpRecoverActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.access_token)
                        //println(response.body()?.token_type)
                        val logintoken = response.body()?.access_token
                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear the error code
                            // Capture the layout's TextView and set the string as its text
                            val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtpRecover).apply {
                                text = ""
                            }
                            //start a new activity here
                            val intent = Intent(this@EmailOtpRecoverActivity, NewPasswordActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, logintoken)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtpRecover).apply {
                                text = "You have given the wrong OTP"
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                        Toast.makeText(this@EmailOtpRecoverActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtpRecover).apply {
                            text = "please check your internet connection"
                        }
                    }

                }
            )
        }
    }
}