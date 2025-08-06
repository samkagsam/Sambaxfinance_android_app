package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLoanStatement
import com.sambaxfinance.sambax.api.ApiInterfaceMyLoanPayments
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoanStatementActivity : AppCompatActivity() {

    lateinit var myAdapterLoanStatement: MyAdapterLoanStatement
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_statement)

        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val loan_id = intent.getStringExtra(EXTRA_MESSAGE_GROUP)

        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLoanStatementActivity)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        val recyclerviewLoanStatement = findViewById<RecyclerView>(R.id.rvLoanStatement)
        recyclerviewLoanStatement.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerviewLoanStatement.layoutManager = linearLayoutManager

        val loanStatementRequestModel = LoanStatementRequestModel(loan_id!!.toInt())

        val response = ServiceBuilder.buildService(ApiInterfaceLoanStatement::class.java)
        response.sendReq(loanStatementRequestModel, "Bearer $token")
            .enqueue(object : Callback<LoanStatementResponseModel> {
                override fun onResponse(
                    call: Call<LoanStatementResponseModel>,
                    response: Response<LoanStatementResponseModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!

                        // Set loan header (optional)
                        //val tvLoanInfo = findViewById<TextView>(R.id.tvLoanInfo)
                        //tvLoanInfo.text = "Loan: ${responseBody.loan_specifics.name}, Balance: ${responseBody.loan_specifics.loan_balance} UGX"

                        val payments = responseBody.payments

                        if (payments.isEmpty()) {
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvSeeEmptyResults)
                            tvSeeEmptyResponse.text = "Oops! You do not have any loan payments!"
                        } else {
                            myAdapterLoanStatement = MyAdapterLoanStatement(baseContext, payments)
                            myAdapterLoanStatement.notifyDataSetChanged()
                            recyclerviewLoanStatement.adapter = myAdapterLoanStatement
                        }
                    } else {
                        Toast.makeText(this@LoanStatementActivity, "No data found", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoanStatementResponseModel>, t: Throwable) {
                    Toast.makeText(this@LoanStatementActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        ActionBarUtils.handleActionBarItemClick(this, item, token)
        return super.onOptionsItemSelected(item)
    }
}
