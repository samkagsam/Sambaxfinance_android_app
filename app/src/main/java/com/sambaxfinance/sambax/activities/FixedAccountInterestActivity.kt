package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceShowFDAInterest
import com.sambaxfinance.sambax.api.ApiInterfaceShowTotalDeposits
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.FixedAccountInterestResponseModel
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupTotalDepositsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixedAccountInterestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_account_interest)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewFixedAccountInterestActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }




        val response = ServiceBuilder.buildService(ApiInterfaceShowFDAInterest::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<FixedAccountInterestResponseModel> {
                override fun onResponse(
                    call: Call<FixedAccountInterestResponseModel>,
                    response: Response<FixedAccountInterestResponseModel>
                ) {
                    Toast.makeText(this@FixedAccountInterestActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())


                    val okResponse = response.message().toString()
                    if (okResponse == "OK"){
                        println("hello")
                        //let us first clear all error codes
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tvShowTotalDeposits).apply {
                            text = "UgX "+ response.body()?.total_interest
                        }


                    }else{
                        println("no hello")
                        //show rejection in textview, refocus user to renter credentials
                        Toast.makeText(this@FixedAccountInterestActivity,"Error in processing request", Toast.LENGTH_LONG).show()
                        // Capture the layout's TextView and set the string as its text
                        val tvSaveResponse = findViewById<TextView>(R.id.tvNoDeposits).apply {
                            text = "There was an error in processing your request"
                        }
                    }
                }

                override fun onFailure(call: Call<FixedAccountInterestResponseModel>, t: Throwable) {
                    Toast.makeText(this@FixedAccountInterestActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    println(t.toString())
                    //show error in error field
                    val tvSaveResponse = findViewById<TextView>(R.id.tvNoDeposits).apply {
                        text = "Please check your internet connection"
                    }
                }

            }
        )
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