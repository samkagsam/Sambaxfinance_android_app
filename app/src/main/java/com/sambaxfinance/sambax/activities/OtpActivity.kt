package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterface
import com.sambaxfinance.sambax.api.ApiInterfaceUserCreate
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.LoginResponseModel
import com.sambaxfinance.sambax.models.RequestModel
import com.sambaxfinance.sambax.models.ResponseModel
import com.sambaxfinance.sambax.models.UserCreateRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity() {

    //private var signtoken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)


        val signtoken = intent.getStringExtra(EXTRA_MESSAGE2)




        // Get the Intent that started this activity and extract the string
        //val signtoken = intent.getStringExtra(EXTRA_MESSAGE2)
        //println(signtoken)

        //introduce variables from layout
        val buttonSendOtp = findViewById<Button>(R.id.buttonSendOtp)
        val otp_given = findViewById<EditText>(R.id.etOtpNumber)

        buttonSendOtp.setOnClickListener {
            val otp = otp_given.text.toString().toIntOrNull() ?: 0

            if(otp == 0){
                otp_given.error = "otp is required"
                otp_given.requestFocus()
                return@setOnClickListener

            }
            //println(signtoken)
            //println(otp)

            println(signtoken)

            val userCreateRequestModel = UserCreateRequestModel(otp)

            val response = ServiceBuilder.buildService(ApiInterfaceUserCreate::class.java)
            response.sendReq(userCreateRequestModel,"Bearer " + signtoken).enqueue(
                object : Callback<LoginResponseModel> {
                    override fun onResponse(
                        call: Call<LoginResponseModel>,
                        response: Response<LoginResponseModel>
                    ) {
                        Toast.makeText(this@OtpActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                            val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtp).apply {
                                text = ""
                            }
                            //start a new activity here
                            val intent = Intent(this@OtpActivity, NewLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, logintoken)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtp).apply {
                                text = "You have given the wrong OTP"
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                        Toast.makeText(this@OtpActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvResponseOtp = findViewById<TextView>(R.id.tvResponseOtp).apply {
                            text = "please check your internet connection"
                        }
                    }

                }
            )
        }
    }
}