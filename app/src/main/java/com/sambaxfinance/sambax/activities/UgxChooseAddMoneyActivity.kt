package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R

class UgxChooseAddMoneyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ugx_choose_add_money)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Adding hamburger icon

        val btn_go_to_use_mobile_money = findViewById<Button>(R.id.btn_go_to_use_mobile_money)
        val btn_go_to_use_card = findViewById<Button>(R.id.btn_go_to_use_card)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        btn_go_to_use_mobile_money.setOnClickListener {

            //start a new activity here
            val intent = Intent(this, UgxAddMoneyMobileActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_go_to_use_card.setOnClickListener {
            //start a new activity here
            val intent = Intent(this, UgxAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
    }
}