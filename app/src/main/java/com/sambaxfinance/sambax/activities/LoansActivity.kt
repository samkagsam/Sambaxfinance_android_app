package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLandingPage
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoansActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loans)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us initiate all the buttons
        val button_apply_for_loan = findViewById<Button>(R.id.button_apply_for_loan)
        val button_go_to_pay_loan = findViewById<Button>(R.id.button_go_to_pay_loan)
        val button_loan_statement = findViewById<Button>(R.id.button_loan_statement)

        val response = ServiceBuilder.buildService(ApiInterfaceLandingPage::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<LandingPageResponseModel> {
                override fun onResponse(
                    call: Call<LandingPageResponseModel>,
                    response: Response<LandingPageResponseModel>
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
                        text = "Loan Balance: UGX "+ response.body()?.loan_balance
                    }
                }

                override fun onFailure(call: Call<LandingPageResponseModel>, t: Throwable) {
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
        button_go_to_pay_loan.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LoansActivity, PayLoanActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_loan_statement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LoansActivity, MyLoanPaymentsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
    }
}