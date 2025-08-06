package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLandingPage
import com.sambaxfinance.sambax.api.ApiInterfaceLoans
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import com.sambaxfinance.sambax.models.LoansResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoansActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loans)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLoansActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiate all the buttons
        val button_apply_for_loan = findViewById<Button>(R.id.button_apply_for_loan)
        val button_go_to_see_loans = findViewById<Button>(R.id.button_go_to_see_loans)
        //val button_loan_statement = findViewById<Button>(R.id.button_loan_statement)

        val response = ServiceBuilder.buildService(ApiInterfaceLoans::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<LoansResponseModel> {
                override fun onResponse(
                    call: Call<LoansResponseModel>,
                    response: Response<LoansResponseModel>
                ) {
                    Toast.makeText(this@LoansActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()


                    // Capture the layout's TextView and set the string as its text
                    val tv_loan_balance = findViewById<TextView>(R.id.tv_loan_balance).apply {
                        text = "Active Loans with Sambax: "+ response.body()?.number_of_active_loans
                    }
                }

                override fun onFailure(call: Call<LoansResponseModel>, t: Throwable) {
                    Toast.makeText(this@LoansActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )
        button_apply_for_loan.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LoansActivity, LoanApplyActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_go_to_see_loans.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LoansActivity, SeeLoansActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

        /*
        button_loan_statement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LoansActivity, MyLoanPaymentsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

         */
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