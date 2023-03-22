package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel

class ShowGroupStatementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_group_statement)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        val group_credentials = intent.getParcelableExtra<GeneralGroupLandingResponseModel>("group_credentials")
        println(group_credentials?.id)
        if (group_credentials != null){
            println(group_credentials.id)
            println("gotten")
            println(token)
        }
    }
}