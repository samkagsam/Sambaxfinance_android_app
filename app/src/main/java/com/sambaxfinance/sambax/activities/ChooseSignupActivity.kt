package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sambaxfinance.sambax.R

class ChooseSignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_signup)

        val btn_go_to_signup_with_phone_number = findViewById<Button>(R.id.btn_go_to_signup_with_phone_number)
        val btn_go_to_signup_with_email_address = findViewById<Button>(R.id.btn_go_to_signup_with_email_address)

        btn_go_to_signup_with_phone_number.setOnClickListener {
            println("you clicked create new account with phone number")
            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
        }
        btn_go_to_signup_with_email_address.setOnClickListener {
            println("you clicked create new account with email address")
            val intent = Intent(this, EmailSignupActivity::class.java)
            startActivity(intent)
        }
    }
}