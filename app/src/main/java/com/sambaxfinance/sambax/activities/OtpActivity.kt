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

    private var signtoken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Get the Intent that started this activity and extract the string
        val first_name_fresh = intent.getStringExtra(FIRST_NAME_ASSIGN)
        val last_name_fresh = intent.getStringExtra(LAST_NAME_ASSIGN)
        val password_fresh = intent.getStringExtra(PASSWORD_ASSIGN)
        val phone_number_fresh = intent.getStringExtra(PHONE_NUMBER_ASSIGN)
        val customer_image_url = intent.getStringExtra(CUSTOMER_IMAGE_URL)
        val customer_id_url = intent.getStringExtra(CUSTOMER_ID_URL)
        //val signtoken = intent.getStringExtra(EXTRA_MESSAGE2)


        //DummyModel
        val requestModel = RequestModel(phone_number_fresh!!.toInt(), password_fresh.toString(),first_name_fresh.toString(),last_name_fresh.toString(),
            customer_image_url.toString(), customer_id_url.toString())

        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Toast.makeText(this@OtpActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                    println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.access_token)
                    //println(response.body()?.token_type)

                    //val signtoken = response.body()?.access_token
                    signtoken = response.body()?.access_token

                    val okResponse = response.message().toString()
                    if (okResponse == "Created"){
                        println("hello")

                        //let us first clear the error field
                        // Capture the layout's TextView and set the string as its text


                    }else{
                        println("no hello")
                        //show rejection in textview, refocus user to renter credentials
                        // Capture the layout's TextView and set the string as its text
                        val tvResponseSignUp = findViewById<TextView>(R.id.tvResponseOtp).apply {
                            text = "Signup rejected.Cause:you are already a user"
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(this@OtpActivity,t.toString(),Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                    // Capture the layout's TextView and set the string as its text
                    val tvResponseSignUp = findViewById<TextView>(R.id.tvResponseOtp).apply {
                        text = "please check your internet connection"
                    }



                }

            }
        )

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
                            val intent = Intent(this@OtpActivity, LandingActivity::class.java).apply {
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