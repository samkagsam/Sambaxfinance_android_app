package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.sambaxfinance.sambax.api.ApiInterfaceFDACreate
import com.sambaxfinance.sambax.api.ApiInterfaceResetPayoutDate
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixedAccountSetPayoutDateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_account_set_payout_date)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewFixedAccountSetPayoutDateActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)

        //introduce variables from layout
        val button_reset_fda_payout = findViewById<Button>(R.id.button_reset_fda_payout)
        val payout_months_given = findViewById<EditText>(R.id.et_reset_fda_months)


        button_reset_fda_payout.setOnClickListener {
            val payout_months = payout_months_given.text.toString().toIntOrNull() ?: 0


            if (payout_months == 0) {
                payout_months_given.error = "Months to save are required"
                payout_months_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(payout_months)

            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val fdaCreateRequestModel = FDACreateRequestModel(payout_months)

            val response = ServiceBuilder.buildService(ApiInterfaceResetPayoutDate::class.java)
            response.sendReq(fdaCreateRequestModel, "Bearer " + token).enqueue(
                object : Callback<FDACreateResponseModel> {
                    override fun onResponse(
                        call: Call<FDACreateResponseModel>,
                        response: Response<FDACreateResponseModel>
                    ) {
                        Toast.makeText(
                            this@FixedAccountSetPayoutDateActivity,
                            response.message().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.id)
                        println(response.body()?.payout_date)

                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "Created") {
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tv_fda_reset_response).apply {
                                    text = ""
                                }

                            //start a new activity here
                            val intent =
                                Intent(this@FixedAccountSetPayoutDateActivity, FixedAccountLandingActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)

                                }
                            startActivity(intent)
                        } else {
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(
                                this@FixedAccountSetPayoutDateActivity,
                                "Error in resetting payout date",
                                Toast.LENGTH_LONG
                            ).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tv_fda_reset_response).apply {
                                    text = "There was an error in resetting the payout date of your fixed deposit account"
                                }
                        }
                    }

                    override fun onFailure(call: Call<FDACreateResponseModel>, t: Throwable) {
                        Toast.makeText(this@FixedAccountSetPayoutDateActivity, t.toString(), Toast.LENGTH_LONG)
                            .show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse =
                            findViewById<TextView>(R.id.tv_fda_reset_response).apply {
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