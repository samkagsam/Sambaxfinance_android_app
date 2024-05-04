package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceNewPassword
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.NewPasswordRequestModel
import com.sambaxfinance.sambax.models.NewPasswordResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //introduce variables from layout
        val buttonSendNewPassword = findViewById<Button>(R.id.buttonSendNewPassword)
        val new_password_given = findViewById<EditText>(R.id.etNewPassword)

        buttonSendNewPassword.setOnClickListener {
            val new_password = new_password_given.text.toString().trim()

            if(new_password.isEmpty()){
                new_password_given.error = "password is required"
                new_password_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(new_password)

            val newPasswordRequestModel = NewPasswordRequestModel(new_password)

            val response = ServiceBuilder.buildService(ApiInterfaceNewPassword::class.java)
            response.sendReq(newPasswordRequestModel,"Bearer " + token).enqueue(
                object : Callback<NewPasswordResponseModel> {
                    override fun onResponse(
                        call: Call<NewPasswordResponseModel>,
                        response: Response<NewPasswordResponseModel>
                    ) {
                        Toast.makeText(this@NewPasswordActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        //println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponseNewPassword).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@NewPasswordActivity, WalletActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@NewPasswordActivity,"Something went wrong", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponseNewPassword).apply {
                                text = "something went wrong with password update"
                            }
                        }
                    }

                    override fun onFailure(call: Call<NewPasswordResponseModel>, t: Throwable) {
                        Toast.makeText(this@NewPasswordActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvResponse = findViewById<TextView>(R.id.tvResponseNewPassword).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )
        }
    }
}