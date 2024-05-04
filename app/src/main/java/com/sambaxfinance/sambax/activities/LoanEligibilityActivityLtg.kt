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
import com.sambaxfinance.sambax.api.ApiInterfaceLtgQuickLoanEligibility
import com.sambaxfinance.sambax.api.ApiInterfaceShowTotalDeposits
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GroupWithdrawRequestModel
import com.sambaxfinance.sambax.models.LongTermGroupTotalDepositsResponseModel
import com.sambaxfinance.sambax.models.LtgQuickLoanEligibilityResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val EXTRA_MESSAGE_LOAN_LIMIT = "com.example.sambax.LOANLIMIT"

class LoanEligibilityActivityLtg : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_eligibility_ltg)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLoanEligibilityActivityLtg)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout
        val button_proceed_to_get_quick_loan = findViewById<Button>(R.id.button_proceed_to_get_quick_loan)



        button_proceed_to_get_quick_loan.setOnClickListener {
            println("wololo")
            println(group_identity)
            //start a new activity here
            val intent = Intent(this@LoanEligibilityActivityLtg, LtgLoanIssueActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_identity)


                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_str)
                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_2)
            }
            startActivity(intent)
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