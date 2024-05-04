package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceFixedAccountDeposit
import com.sambaxfinance.sambax.api.ApiInterfaceSave
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FixedAccountDepositRequestModel
import com.sambaxfinance.sambax.models.FixedAccountDepositResponseModel
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixedAccountDepositActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_account_deposit)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewFixedAccountDepositActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)

        //introduce variables from layout
        val buttonSaveMoney = findViewById<Button>(R.id.button_fda_deposit_money)
        val deposit_money_given = findViewById<EditText>(R.id.et_fda_deposit)


        buttonSaveMoney.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonSaveMoney.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonSaveMoney.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            val deposit_money = deposit_money_given.text.toString().toIntOrNull() ?: 0

            if(deposit_money == 0){
                deposit_money_given.error = "Amount to save is required"
                deposit_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(deposit_money)

            val fixedAccountDepositRequestModel = FixedAccountDepositRequestModel(deposit_money)

            val response = ServiceBuilder.buildService(ApiInterfaceFixedAccountDeposit::class.java)
            response.sendReq(fixedAccountDepositRequestModel,"Bearer " + token).enqueue(
                object : Callback<FixedAccountDepositResponseModel> {
                    override fun onResponse(
                        call: Call<FixedAccountDepositResponseModel>,
                        response: Response<FixedAccountDepositResponseModel>
                    ) {
                        Toast.makeText(this@FixedAccountDepositActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_fd_deposit_response).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@FixedAccountDepositActivity, FixedAccountLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@FixedAccountDepositActivity,"Error in processing deposit", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tv_fd_deposit_response).apply {
                                text = "There was an error in processing your deposit"
                            }
                        }
                    }

                    override fun onFailure(call: Call<FixedAccountDepositResponseModel>, t: Throwable) {
                        Toast.makeText(this@FixedAccountDepositActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_fd_deposit_response).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        // Handle action bar item clicks here.
        ActionBarUtils.handleActionBarItemClick(this, item, token)

        return super.onOptionsItemSelected(item)

    }
}