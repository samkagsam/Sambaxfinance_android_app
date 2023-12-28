package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sambaxfinance.sambax.R

class ChooseSendMoneyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_send_money)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        val btn_go_to_send_money_within_uganda = findViewById<Button>(R.id.btn_go_to_send_money_within_uganda)
        val btn_go_to_send_money_to_uganda = findViewById<Button>(R.id.btn_go_to_send_money_to_uganda)
        val btn_go_to_internal_funds_transfer = findViewById<Button>(R.id.btn_go_to_internal_funds_transfer)

        btn_go_to_send_money_within_uganda.setOnClickListener {
            println("you clicked got to send money within uganda")
            //start a new activity here
            val intent = Intent(this@ChooseSendMoneyActivity, SendMoneyWithinUgandaActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_go_to_send_money_to_uganda.setOnClickListener {
            println("you clicked go to send money to uganda")
            //start a new activity here
            val intent = Intent(this@ChooseSendMoneyActivity, SendMoneyToUgandaActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_go_to_internal_funds_transfer.setOnClickListener {
            println("you clicked go to transfer activity")
            //start a new activity here
            val intent = Intent(this@ChooseSendMoneyActivity, TransferActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
    }
}