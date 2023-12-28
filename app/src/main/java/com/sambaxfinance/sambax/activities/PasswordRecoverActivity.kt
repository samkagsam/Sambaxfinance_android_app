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
import com.sambaxfinance.sambax.api.ApiInterfaceForgotPassword
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.ForgotPasswordRequestModel
import com.sambaxfinance.sambax.models.ForgotPasswordResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val EXTRA_MESSAGE3 = "com.example.sambax.MESSAGE3"

class PasswordRecoverActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recover)

        //let us get variables from layout
        val buttonSendPhonenumber = findViewById<Button>(R.id.buttonSendPhoneNumber)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumberRecover)


        buttonSendPhonenumber.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonSendPhonenumber.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonSendPhonenumber.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            //val phone_number_fresh = phone_number_given.text.toString().toIntOrNull() ?: 0
            val phone_number_fresh = phone_number_given.text.toString().trim()

            /*
            if(phone_number_fresh == 0){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }*/

            if(phone_number_fresh.isEmpty()){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            val user_email = "0"
            //val phone_number_string = phone_number_fresh.toString()

            //let us send request to our server
            val requestModel = ForgotPasswordRequestModel(phone_number_fresh, user_email)

            val response = ServiceBuilder.buildService(ApiInterfaceForgotPassword::class.java)
            response.sendReq(requestModel).enqueue(
                object : Callback<ForgotPasswordResponseModel> {
                    override fun onResponse(
                        call: Call<ForgotPasswordResponseModel>,
                        response: Response<ForgotPasswordResponseModel>
                    ) {
                        Toast.makeText(this@PasswordRecoverActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.access_token)
                        //println(response.body()?.token_type)
                        val token = response.body()?.access_token
                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //clear error field
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponsePhoneNumberRecover).apply {
                                text = ""
                            }
                            //start a new activity here
                            val intent = Intent(this@PasswordRecoverActivity, OtpRecoverActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE3, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            //val wrongCredentialsMessage = "Wrong phone number or password"
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponsePhoneNumberRecover).apply {
                                text = "Phone number not registered with Sambax Finance"
                            }
                            phone_number_given.requestFocus()
                            //return@setOnClickListener
                            Toast.makeText(this@PasswordRecoverActivity,"Phone number not registered with Sambax Finance",
                                Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<ForgotPasswordResponseModel>, t: Throwable) {
                        Toast.makeText(this@PasswordRecoverActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvResponse = findViewById<TextView>(R.id.tvResponsePhoneNumberRecover).apply {
                            text = "please check your internet connection"
                        }
                    }

                }
            )

        }
    }
}