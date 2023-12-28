package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sambaxfinance.sambax.R

class ChoosePasswordRecoverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_password_recover)

        val btn_go_to_recover_with_phone_number = findViewById<Button>(R.id.btn_go_to_recover_with_phone_number)
        val btn_go_to_recover_with_email_address = findViewById<Button>(R.id.btn_go_to_recover_with_email_address)

        btn_go_to_recover_with_phone_number.setOnClickListener {
            println("you clicked recover password with phone number")
            val intent = Intent(this, PasswordRecoverActivity::class.java)
            startActivity(intent)
        }
        btn_go_to_recover_with_email_address.setOnClickListener {
            println("you clicked recover password with email address")
            val intent = Intent(this, EmailPasswordRecoverActivity::class.java)
            startActivity(intent)
        }

    }
}