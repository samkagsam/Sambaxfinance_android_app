package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.hbb20.CountryCodePicker
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterface
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.RequestModel
import com.sambaxfinance.sambax.models.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val EXTRA_MESSAGE_EMAIL_SIGN_OTP_TOKEN = "com.example.sambax.EXTRA_MESSAGE_EMAIL_SIGN_OTP_TOKEN"
//const val EXTRA_MESSAGE2 = "com.example.sambax.MESSAGE2"

class EmailSignupActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_signup)

        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val first_name_given = findViewById<EditText>(R.id.etFirstNameEmail)
        val last_name_given = findViewById<EditText>(R.id.etLastNameEmail)
        val password_given = findViewById<EditText>(R.id.etPassword2Email)
        val email_given = findViewById<EditText>(R.id.etYourEmail)

        val countryCodePicker = findViewById<CountryCodePicker>(R.id.countryCodePicker)

        // Assuming you have initialized your spinners in your Activity or Fragment
        val spinnerSex: Spinner = findViewById(R.id.spinnerSex)


        buttonSignUp.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonSignUp.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonSignUp.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            // Get the selected item from the "Sex" spinner
            val sex: String = spinnerSex.selectedItem as String


            // Move the retrieval of selectedCountryName and selectedCountryCode here
            val selectedCountryName: String = countryCodePicker.selectedCountryName
            val selectedCountryCode: String = countryCodePicker.selectedCountryCode


            //let us get the form variables
            val first_name_fresh = first_name_given.text.toString().trim()
            val last_name_fresh = last_name_given.text.toString().trim()
            val password_fresh = password_given.text.toString().trim()
            val email_fresh = email_given.text.toString().trim()
            //val phone_number_fresh = phone_number_given.text.toString().toIntOrNull() ?: 0
            val phone_number_fresh = "0"




            if(first_name_fresh.isEmpty()){
                first_name_given.error = "first name is required"
                first_name_given.requestFocus()
                return@setOnClickListener

            }

            if(last_name_fresh.isEmpty()){
                last_name_given.error = "last name is required"
                last_name_given.requestFocus()
                return@setOnClickListener

            }
            /*
            if(phone_number_fresh == 0){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }*/
            if(email_fresh.isEmpty()){
                email_given.error = "email address is required"
                email_given.requestFocus()
                return@setOnClickListener

            }

            if(password_fresh.isEmpty()){
                password_given.error = "password is required"
                password_given.requestFocus()
                return@setOnClickListener

            }

            println(selectedCountryName)
            println(selectedCountryCode)

            //DummyModel
            val requestModel = RequestModel(first_name_fresh,last_name_fresh,phone_number_fresh,email_fresh,password_fresh, selectedCountryName, selectedCountryCode, sex
            )

            val response = ServiceBuilder.buildService(ApiInterface::class.java)
            response.sendReq(requestModel).enqueue(
                object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        Toast.makeText(this@EmailSignupActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.access_token)
                        //println(response.body()?.token_type)

                        //val signtoken = response.body()?.access_token
                        val signtoken = response.body()?.access_token

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")

                            //let us first clear the error field
                            // Capture the layout's TextView and set the string as its text

                            //startActivity(intent)
                            val intent = Intent(this@EmailSignupActivity, OtpActivity::class.java).apply {
                                //putExtra(EXTRA_MESSAGE_EMAIL_SIGN_OTP_TOKEN, signtoken)
                                putExtra(EXTRA_MESSAGE2, signtoken)

                            }
                            startActivity(intent)


                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            val tvResponseSignUp = findViewById<TextView>(R.id.tvResponseSignUp).apply {
                                text = "Signup rejected.Cause:you are already a user"
                            }

                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Toast.makeText(this@EmailSignupActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvResponseSignUp = findViewById<TextView>(R.id.tvResponseSignUp).apply {
                            text = "please check your internet connection"
                        }



                    }

                }
            )









        }
    }
}