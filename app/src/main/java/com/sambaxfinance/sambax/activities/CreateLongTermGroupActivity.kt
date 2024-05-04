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
import com.sambaxfinance.sambax.api.ApiInterfaceCreateLongTermGroup
import com.sambaxfinance.sambax.api.ApiInterfaceFDACreate
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.CreateLongTermGroupRequestModel
import com.sambaxfinance.sambax.models.FDACreateRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateLongTermGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_long_term_group)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewCreateLongTermGroupActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }



        //introduce variables from layout
        val buttonCreateLTG = findViewById<Button>(R.id.buttonCreateLTG)
        val payout_months_given = findViewById<EditText>(R.id.et_ltg_months)
        val group_name_given = findViewById<EditText>(R.id.et_enter_ltg_name)


        buttonCreateLTG.setOnClickListener {
            val payout_months = payout_months_given.text.toString().toIntOrNull() ?: 0
            val group_name_to_use = group_name_given.text.toString().trim()


            if (payout_months == 0) {
                payout_months_given.error = "Months to save are required"
                payout_months_given.requestFocus()
                return@setOnClickListener

            }

            if(group_name_to_use.isEmpty()){
                group_name_given.error = "group name is required"
                group_name_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(payout_months)

            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val createLongTermGroupRequestModel = CreateLongTermGroupRequestModel(payout_months, group_name_to_use)

            val response = ServiceBuilder.buildService(ApiInterfaceCreateLongTermGroup::class.java)
            response.sendReq(createLongTermGroupRequestModel, "Bearer " + token).enqueue(
                object : Callback<FDACreateResponseModel> {
                    override fun onResponse(
                        call: Call<FDACreateResponseModel>,
                        response: Response<FDACreateResponseModel>
                    ) {
                        Toast.makeText(
                            this@CreateLongTermGroupActivity,
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
                                findViewById<TextView>(R.id.tv_LTG_create_response).apply {
                                    text = ""
                                }

                            //start a new activity here
                            val intent =
                                Intent(this@CreateLongTermGroupActivity, LongTermGroupLandingActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)
                                    putExtra(EXTRA_MESSAGE_GROUP, response.body()?.id.toString())

                                }
                            startActivity(intent)
                        } else {
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(
                                this@CreateLongTermGroupActivity,
                                "Error in creating fixed deposit account",
                                Toast.LENGTH_LONG
                            ).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tv_LTG_create_response).apply {
                                    text = "There was an error in creating your long term group"
                                }
                        }
                    }

                    override fun onFailure(call: Call<FDACreateResponseModel>, t: Throwable) {
                        Toast.makeText(this@CreateLongTermGroupActivity, t.toString(), Toast.LENGTH_LONG)
                            .show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse =
                            findViewById<TextView>(R.id.tv_LTG_create_response).apply {
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