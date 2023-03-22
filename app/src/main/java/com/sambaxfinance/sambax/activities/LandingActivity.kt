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
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        //let us initiate all the buttons
        val buttonApply = findViewById<Button>(R.id.buttonApply)
        val buttonPay = findViewById<Button>(R.id.buttonPay)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonWithdraw = findViewById<Button>(R.id.buttonWithdraw)
        val buttonGoToGroupSavings = findViewById<Button>(R.id.buttonGoToGroupSavings)
        val buttonViewLoanPayments = findViewById<Button>(R.id.buttonViewLoanPayments)
        val buttonLoanCalculator = findViewById<Button>(R.id.buttonLoanCalculator)
        val buttonStatement = findViewById<Button>(R.id.buttonStatement)
        val buttonFixedDeposit = findViewById<Button>(R.id.buttonFixedDepositAccount)
        val buttonTransferMoney = findViewById<Button>(R.id.buttonTransferMoney)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)


        val response = ServiceBuilder.buildService(ApiInterfaceLandingPage::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<LandingPageResponseModel> {
                override fun onResponse(
                    call: Call<LandingPageResponseModel>,
                    response: Response<LandingPageResponseModel>
                ) {
                    Toast.makeText(this@LandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()

                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.tvHello).apply {
                        text = "Hello "+ response.body()?.first_name
                    }

                    // Capture the layout's TextView and set the string as its text
                    val tv_account_balance = findViewById<TextView>(R.id.tvAccountBalance).apply {
                        text = "Account Balance: "+ response.body()?.account_balance
                    }
                    // Capture the layout's TextView and set the string as its text
                    val tv_loan_balance = findViewById<TextView>(R.id.tvLoanBalance).apply {
                        text = "Loan Balance: "+ response.body()?.loan_balance
                    }
                }

                override fun onFailure(call: Call<LandingPageResponseModel>, t: Throwable) {
                    Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )

        //val weeklyGroupRequests = WeeklyGroupRequests.getTotalWeeklyGroupRequests(token.toString())


        //val longTermGroupRequests = LongTermGroupRequests.getTotalLongTermGroupRequests(token.toString())
        //val totalRequests = weeklyGroupRequests + longTermGroupRequests

        // Capture the layout's TextView and set the string as its text
        //val tv_group_requests = findViewById<TextView>(R.id.tvShowWeeklyGroupNotifications).apply {
        //    text = totalRequests.toString()
        //}

        getTotalWeeklyGroupRequests(token.toString())
        getTotalLongTermGroupRequests(token.toString())


        buttonApply.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, LoanApplyActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonPay.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, PayLoanActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

        buttonSave.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, SaveActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonWithdraw.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, WithdrawActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonGoToGroupSavings.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, GroupTypeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonViewLoanPayments.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, MyLoanPaymentsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonLoanCalculator.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, CalculatorActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonStatement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, StatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        buttonFixedDeposit.setOnClickListener {


            val fda_response = ServiceBuilder.buildService(ApiInterfaceFixedAccountLanding::class.java)
            fda_response.sendReq("Bearer " + token).enqueue(
                object : Callback<FixedAccountLandingResponseModel> {
                    override fun onResponse(
                        call: Call<FixedAccountLandingResponseModel>,
                        response: Response<FixedAccountLandingResponseModel>
                    ) {
                        Toast.makeText(this@LandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        //println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.first_name)
                        //println(response.body()?.account_balance)
                        //println(response.body()?.loan_balance)
                        //val logintoken = response.body()?.access_token
                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "OK"){
                            println("hello")
                            //start a new activity here
                            val intent = Intent(this@LandingActivity, FixedAccountLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)


                        }else{
                            println("no hello")

                            //start a new activity here
                            val intent = Intent(this@LandingActivity, FixedAccountActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }



                    }

                    override fun onFailure(call: Call<FixedAccountLandingResponseModel>, t: Throwable) {
                        Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                        //println("we failed")
                        //println(t.toString())
                    }

                }
            )
        }
        buttonTransferMoney.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LandingActivity, TransferActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {
            //Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this@LandingActivity, AboutActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_two) {
            //Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this@LandingActivity, TermsActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_three) {
            //Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this@LandingActivity, UserGuideActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_four) {
            //Toast.makeText(this, "Item four Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this@LandingActivity, ContactUsActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_five) {
            //start a new activity here
            val intent = Intent(this@LandingActivity, MyLoanApplicationsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    fun getTotalWeeklyGroupRequests(token:String){

        val response = ServiceBuilder.buildService(ApiInterfaceGetTotalGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<GetTotalRequestsResponseModel> {
                override fun onResponse(
                    call: Call<GetTotalRequestsResponseModel>,
                    response: Response<GetTotalRequestsResponseModel>
                ) {

                    val okResponse = response.message().toString()

                    val request_total = response.body()?.total_requests
                    //println(WeeklyGroupRequests.request_total)

                    //return request_total

                    if(request_total != 0){
                        // Capture the layout's TextView and set the string as its text
                        val tv_group_requests = findViewById<TextView>(R.id.tvShowWeeklyGroupNotifications).apply {
                            text = request_total.toString()
                        }
                    }






                }

                override fun onFailure(call: Call<GetTotalRequestsResponseModel>, t: Throwable) {
                    //Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )


        //println(request_total)
        println("eya")

        //return request_total!!.toInt()
        //return 3

    }

    fun getTotalLongTermGroupRequests(token:String){

        val response = ServiceBuilder.buildService(ApiInterfaceGetTotalLongTermGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<GetTotalRequestsResponseModel> {
                override fun onResponse(
                    call: Call<GetTotalRequestsResponseModel>,
                    response: Response<GetTotalRequestsResponseModel>
                ) {

                    val okResponse = response.message().toString()
                    val request_total = response.body()?.total_requests
                    println(request_total)

                    if(request_total != 0){
                        // Capture the layout's TextView and set the string as its text
                        val tv_group_requests = findViewById<TextView>(R.id.tvShowLongTermGroupNotifications).apply {
                            text = request_total.toString()
                        }
                    }




                }

                override fun onFailure(call: Call<GetTotalRequestsResponseModel>, t: Throwable) {
                    //Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )


        //println(request_total)
        println("yalelo")


        //return request_total!!.toInt()
        //return 2

    }
}