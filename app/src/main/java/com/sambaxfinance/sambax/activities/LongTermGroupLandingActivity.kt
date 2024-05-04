package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermGroupLandingActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_group_landing)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLongTermGroupLandingActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiate all the buttons
        val buttonGroupDeposit = findViewById<Button>(R.id.buttonLongTermGroupDeposit)
        val buttonGroupWithdraw = findViewById<Button>(R.id.buttonLongTermGroupWithdraw)
        val buttonGroupStatement = findViewById<Button>(R.id.buttonLongTermGroupStatement)
        val buttonSeeGroupMembers = findViewById<Button>(R.id.buttonSeeLongTermGroupMembers)
        val buttonAddGroupMembers = findViewById<Button>(R.id.buttonAddLongTermGroupMembers)
        val button_see_my_personal_statement = findViewById<Button>(R.id.button_see_my_personal_statement)
        //val button_see_my_total_deposits = findViewById<Button>(R.id.button_see_my_total_deposits)
        //val button_see_ltg_interest = findViewById<Button>(R.id.button_see_ltg_interest)
        val button_set_new_ltg_payout_date = findViewById<Button>(R.id.button_set_new_ltg_payout_date)
        val button_get_quick_loan_from_ltg = findViewById<Button>(R.id.button_get_quick_loan_from_ltg)
        val button_change_ltg_name = findViewById<Button>(R.id.button_change_ltg_name)
        val button_pay_ltg_loan = findViewById<Button>(R.id.button_pay_ltg_loan)
        val button_ltg_members_with_loans = findViewById<Button>(R.id.button_ltg_members_with_loans)
        val button_go_to_remove_ltg_member = findViewById<Button>(R.id.button_go_to_remove_ltg_member)
        val button_go_to_change_ltg_interest_rate = findViewById<Button>(R.id.button_go_to_change_ltg_interest_rate)




        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity_2 = intent.getStringExtra(EXTRA_MESSAGE_GROUP)

        val group_credentials = intent.getParcelableExtra<GeneralGroupLandingResponseModel>("group_credentials")
        val group_identity = group_credentials?.id
        val group_identity_str = group_identity.toString()

        var group_number = ""

        if (group_identity_2 == null){
            group_number = group_identity_str
        }

        if (group_credentials == null){
            group_number = group_identity_2!!
        }


        val groupLandingRequestModel = GroupLandingRequestModel(group_number.toInt())
        val response = ServiceBuilder.buildService(ApiInterfaceLongTermGroupLanding::class.java)
        response.sendReq(groupLandingRequestModel, "Bearer " + token).enqueue(
            object : Callback<LongTermGroupLandingResponseModel> {
                override fun onResponse(
                    call: Call<LongTermGroupLandingResponseModel>,
                    response: Response<LongTermGroupLandingResponseModel>
                ) {
                    Toast.makeText(this@LongTermGroupLandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()
                    println(okResponse)

                    if(okResponse == "Forbidden"){
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroup).apply {
                            text = "Oops! You do not belong to any saving group!"
                        }

                    } else{
                        val responseBody = response.body()!!
                        // Capture the layout's TextView and set the string as its text
                        val tvGroupName = findViewById<TextView>(R.id.tvGroupDetails).apply {
                            text = ""+ response.body()?.group_name
                        }

                        val tvCurrentCycle = findViewById<TextView>(R.id.tvLongTermGroupCurrentCycle).apply {
                            text = "Current Cycle: "+ response.body()?.current_cycle
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tvGroupAccountBalance = findViewById<TextView>(R.id.tvLongTermGroupAccountBalance).apply {
                            text = "Group Account Balance: "+ response.body()?.group_account_balance
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_personal_stake_in_ltg = findViewById<TextView>(R.id.tv_personal_stake_in_ltg).apply {
                            text = "Personal Stake: "+ response.body()?.personal_stake
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_ltg_loans = findViewById<TextView>(R.id.tv_ltg_loans).apply {
                            text = "Group Loans: "+ response.body()?.group_loans
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_ltg_profit = findViewById<TextView>(R.id.tv_ltg_profit).apply {
                            text = "Group Profit: "+ response.body()?.group_profit
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_ltg_loan_balance = findViewById<TextView>(R.id.tv_ltg_loan_balance).apply {
                            text = "Loan Balance: "+ response.body()?.loan_balance
                        }
                        // Capture the layout's TextView and set the string as its text
                        val tv_ltg_loan_interest_rate = findViewById<TextView>(R.id.tv_ltg_loan_interest_rate).apply {
                            text = "Loan Interest Rate: "+ response.body()?.interest_rate
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tv_ltg_group_admin = findViewById<TextView>(R.id.tv_ltg_group_admin).apply {
                            text = "Group Admin:"+ response.body()?.group_admin
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tvGroupPayout = findViewById<TextView>(R.id.tv_lg_payout_date).apply {
                            text = "Group Payout: "+ response.body()?.payout_date
                        }


                    }


                }

                override fun onFailure(call: Call<LongTermGroupLandingResponseModel>, t: Throwable) {
                    Toast.makeText(this@LongTermGroupLandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                    // Capture the layout's TextView and set the string as its text
                    val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroup).apply {
                        text = "Oops! Please check your internet connection!"
                    }
                }

            }
        )


        buttonGroupDeposit.setOnClickListener {
            println("wololo")
            println(group_number)
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupDepositActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)

                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_str)
                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_2)
            }
            startActivity(intent)
        }
        buttonGroupWithdraw.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonGroupWithdraw .isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonGroupWithdraw.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            val groupWithdrawRequestModel = GroupWithdrawRequestModel(group_number.toInt())

            val response = ServiceBuilder.buildService(ApiInterfaceLongTermGroupWithdraw::class.java)
            response.sendReq(groupWithdrawRequestModel,"Bearer " + token).enqueue(
                object : Callback<GroupWithdrawResponseModel> {
                    override fun onResponse(
                        call: Call<GroupWithdrawResponseModel>,
                        response: Response<GroupWithdrawResponseModel>
                    ) {
                        Toast.makeText(this@LongTermGroupLandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        //println("we were successful")

                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "Created"){
                            println("hello")

                            //start a new activity here
                            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_number)
                            }
                            startActivity(intent)

                        }else{
                            println("no hello")

                            //start a new activity here
                            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupWithdrawActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                            }
                            startActivity(intent)

                        }


                    }

                    override fun onFailure(call: Call<GroupWithdrawResponseModel>, t: Throwable) {
                        Toast.makeText(this@LongTermGroupLandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                        //println("we failed")
                        //println(t.toString())
                    }

                }
            )
        }

        buttonGroupStatement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupGeneralStatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        buttonSeeGroupMembers.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupMembersActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        buttonAddGroupMembers.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, AddLongTermGroupMembersActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        button_see_my_personal_statement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, PersonalGroupStatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_set_new_ltg_payout_date.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LongTermGroupSetPayoutDateActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_get_quick_loan_from_ltg.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LoanEligibilityActivityLtg::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_change_ltg_name.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, ChangeLtgNameActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_pay_ltg_loan.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, PayLtgLoanActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_ltg_members_with_loans.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, LtgMembersWithLoansActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_go_to_remove_ltg_member.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, RemoveLtgMemberActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }

        button_go_to_change_ltg_interest_rate.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermGroupLandingActivity, ChangeLtgInterestActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
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