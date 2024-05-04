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
import com.sambaxfinance.sambax.api.ApiInterfaceUgxWithdrawByMobileMoney
import com.sambaxfinance.sambax.api.ApiInterfaceWithdraw
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.StatementResponseModel
import com.sambaxfinance.sambax.models.TransactionRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UgxWithdrawByMobileMoneyActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler() // Initialize the Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ugx_withdraw_by_mobile_money)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewUgxWithdrawByMobileMoneyActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }
        //introduce variables from layout
        val buttonWithdrawMoney = findViewById<Button>(R.id.buttonUgxWithdrawMoney)
        val withdraw_money_given = findViewById<EditText>(R.id.etAddMoney)


        buttonWithdrawMoney.setOnClickListener {

            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonWithdrawMoney.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonWithdrawMoney.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            println("button now disabled")


            val withdraw_money = withdraw_money_given.text.toString().toIntOrNull() ?: 0

            if(withdraw_money == 0){
                withdraw_money_given.error = "Amount to withdraw is required"
                withdraw_money_given.requestFocus()
                return@setOnClickListener


            }
            println(token)
            println(withdraw_money)

            val transactionRequestModel = TransactionRequestModel(withdraw_money)

            val response = ServiceBuilder.buildService(ApiInterfaceUgxWithdrawByMobileMoney::class.java)
            response.sendReq(transactionRequestModel,"Bearer " + token).enqueue(
                object : Callback<StatementResponseModel> {
                    override fun onResponse(
                        call: Call<StatementResponseModel>,
                        response: Response<StatementResponseModel>
                    ) {
                        Toast.makeText(this@UgxWithdrawByMobileMoneyActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                            val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@UgxWithdrawByMobileMoneyActivity, WalletActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)



                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@UgxWithdrawByMobileMoneyActivity,"Error in processing withdraw request", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = response.message().toString()
                            }



                        }
                    }

                    override fun onFailure(call: Call<StatementResponseModel>, t: Throwable) {
                        Toast.makeText(this@UgxWithdrawByMobileMoneyActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvWithdrawResponse = findViewById<TextView>(R.id.tvResponse).apply {
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