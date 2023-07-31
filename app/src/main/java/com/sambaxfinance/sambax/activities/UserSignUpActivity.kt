package com.sambaxfinance.sambax.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterface
import com.sambaxfinance.sambax.api.MyApi
import com.sambaxfinance.sambax.api.MyApi2
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


const val EXTRA_MESSAGE2 = "com.example.sambax.MESSAGE2"
const val FIRST_NAME_ASSIGN = "com.sambax.sambax.first_name_assign"
const val LAST_NAME_ASSIGN = "com.sambax.sambax.last_name_assign"
const val PASSWORD_ASSIGN = "com.sambax.sambax.password_assign"
const val PHONE_NUMBER_ASSIGN = "com.sambax.sambax.phone_number_assign"

class UserSignUpActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign_up)




        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val first_name_given = findViewById<EditText>(R.id.etFirstName)
        val last_name_given = findViewById<EditText>(R.id.etLastName)
        val password_given = findViewById<EditText>(R.id.etPassword2)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumber2)
        //val tvResponseSignUp = findViewById<TextView>(R.id.tvResponseSignUp)
        //val tvLogin = findViewById<TextView>(R.id.tvLogin)

        buttonSignUp.setOnClickListener {
            //let us deactivate the signup button
            //it.isClickable = false     // to disable clicking on button
            //it.isEnabled = false       // to disable button
            buttonSignUp.isEnabled = false
            buttonSignUp.isClickable = false


            //let us get the form variables
            val first_name_fresh = first_name_given.text.toString().trim()
            val last_name_fresh = last_name_given.text.toString().trim()
            val password_fresh = password_given.text.toString().trim()
            //val phone_number_fish = phone_number.text.toString().trim()
            val phone_number_fresh = phone_number_given.text.toString().toIntOrNull() ?: 0



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
            if(phone_number_fresh == 0){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            if(password_fresh.isEmpty()){
                password_given.error = "password is required"
                password_given.requestFocus()
                return@setOnClickListener

            }

            //DummyModel
            val requestModel = RequestModel(phone_number_fresh, password_fresh,first_name_fresh,last_name_fresh,
                "google_refused", "google_refused")

            val response = ServiceBuilder.buildService(ApiInterface::class.java)
            response.sendReq(requestModel).enqueue(
                object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        Toast.makeText(this@UserSignUpActivity,response.message().toString(),Toast.LENGTH_LONG).show()
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
                            val intent = Intent(this@UserSignUpActivity, OtpActivity::class.java).apply {
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
                        Toast.makeText(this@UserSignUpActivity,t.toString(),Toast.LENGTH_LONG).show()
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