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


const val EXTRA_MESSAGE_GROUP = "com.example.sambax.Groupid"
class GroupActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewGroupActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiate all the buttons
        val buttonGroupDeposit = findViewById<Button>(R.id.buttonGroupDeposit)
        val buttonGroupWithdraw = findViewById<Button>(R.id.buttonGroupWithdraw)
        val buttonGroupStatement = findViewById<Button>(R.id.buttonGroupStatement)
        val buttonSeeGroupMembers = findViewById<Button>(R.id.buttonSeeGroupMembers)
        val buttonAddGroupMembers = findViewById<Button>(R.id.buttonAddGroupMembers)
        val button_change_group_name = findViewById<Button>(R.id.button_change_group_name)
        val button_go_to_remove_weekly_group_member = findViewById<Button>(R.id.button_go_to_remove_weekly_group_member)
        val button_go_to_change_member_contribution = findViewById<Button>(R.id.button_go_to_change_member_contribution)
        val button_see_total_member_contributions = findViewById<Button>(R.id.button_see_total_member_contributions)



        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity_2 = intent.getStringExtra(EXTRA_MESSAGE_GROUP)

        val group_credentials = intent.getParcelableExtra<GeneralGroupLandingResponseModel>("group_credentials")
        val group_identity = group_credentials?.id
        val group_identity_str = group_identity.toString()
        //val group_identity_2_str = group_identity_2.toString()
        //println(group_credentials?.id)
        //println(group_identity_str)

        var group_number = ""

        if (group_identity_2 == null){
            group_number = group_identity_str
        }

        if (group_credentials == null){
            group_number = group_identity_2!!
        }

        val groupLandingRequestModel = GroupLandingRequestModel(group_number.toInt())
        val response = ServiceBuilder.buildService(ApiInterfaceGroupLanding::class.java)
        response.sendReq(groupLandingRequestModel, "Bearer " + token).enqueue(
            object : Callback<GroupLandingResponseModel> {
                override fun onResponse(
                    call: Call<GroupLandingResponseModel>,
                    response: Response<GroupLandingResponseModel>
                ) {
                    Toast.makeText(this@GroupActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")

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
                        val tvShowWeeklyGroupName = findViewById<TextView>(R.id.tvShowWeeklyGroupName).apply {
                            text = ""+ response.body()?.group_name
                        }

                        // Capture the layout's TextView and set the string as its text
                        val tvGroupPayout = findViewById<TextView>(R.id.tvGroupPayout).apply {
                            text = "Group Payout: "+ response.body()?.group_payout
                        }
                        // Capture the layout's TextView and set the string as its text
                        val tvGroupAccountBalance = findViewById<TextView>(R.id.tvGroupAccountBalance).apply {
                            text = "Group Account Balance: "+ response.body()?.group_account_balance
                        }
                        val tvCurrentWeek = findViewById<TextView>(R.id.tvCurrentWeek).apply {
                            text = "Current Week: "+ response.body()?.current_week
                        }
                        val tvCurrentCycle = findViewById<TextView>(R.id.tvCurrentCycle).apply {
                            text = "Current Cycle: "+ response.body()?.current_cycle
                        }
                        val tvWeeklyContribution = findViewById<TextView>(R.id.tvWeeklyContribution).apply {
                            text = "Weekly Member Contribution: "+ response.body()?.weekly_contribution
                        }

                        val tv_weekly_group_admin = findViewById<TextView>(R.id.tv_weekly_group_admin).apply {
                            text = "Group Admin: "+ response.body()?.group_admin
                        }

                        val tvCurrentBeneficiary = findViewById<TextView>(R.id.tvCurrentBeneficiary).apply {
                            text = "This week's beneficiary is: "+ response.body()?.week_beneficiary
                        }

                    }


                }

                override fun onFailure(call: Call<GroupLandingResponseModel>, t: Throwable) {
                    Toast.makeText(this@GroupActivity,t.toString(), Toast.LENGTH_LONG).show()
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
            val intent = Intent(this@GroupActivity, GroupDepositActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)


            }
            startActivity(intent)
        }
        buttonGroupWithdraw.setOnClickListener {
            /*
            //start a new activity here
            val intent = Intent(this@GroupActivity, GroupWithdrawActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
            */
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonGroupWithdraw .isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonGroupWithdraw .isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            val groupWithdrawRequestModel = GroupWithdrawRequestModel(group_number.toInt())

            val response = ServiceBuilder.buildService(ApiInterfaceGroupWithdraw::class.java)
            response.sendReq(groupWithdrawRequestModel,"Bearer " + token).enqueue(
                object : Callback<GroupWithdrawResponseModel> {
                    override fun onResponse(
                        call: Call<GroupWithdrawResponseModel>,
                        response: Response<GroupWithdrawResponseModel>
                    ) {
                        Toast.makeText(this@GroupActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        //println("we were successful")

                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "Created"){
                            println("hello")
                            //clear error field
                            /*// Capture the layout's TextView and set the string as its text
                            val tvresponse = findViewById<TextView>(R.id.tvGroupWithdrawPositiveResponse).apply {
                                text = "You have successfully withdrawn your weekly payout"
                            }*/
                            //start a new activity here
                            val intent = Intent(this@GroupActivity, GroupActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_number)
                            }
                            startActivity(intent)

                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            //val wrongCredentialsMessage = "Wrong phone number or password"
                            // Capture the layout's TextView and set the string as its text
                            /*val tvResponse = findViewById<TextView>(R.id.tvGroupWithdrawNegativeResponse).apply {
                                text = "Withdrawal not allowed"
                            }

                            //return@setOnClickListener
                            Toast.makeText(this@GroupWithdrawActivity,"Withdrawal not allowed",Toast.LENGTH_LONG).show()
                            */
                            //start a new activity here
                            val intent = Intent(this@GroupActivity, FeedBackActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                //putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                            }
                            startActivity(intent)

                        }


                    }

                    override fun onFailure(call: Call<GroupWithdrawResponseModel>, t: Throwable) {
                        Toast.makeText(this@GroupActivity,t.toString(), Toast.LENGTH_LONG).show()
                        //println("we failed")
                        //println(t.toString())
                    }

                }
            )
        }

        buttonGroupStatement.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, GroupStatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        buttonSeeGroupMembers.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, GroupMembersActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        buttonAddGroupMembers.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, AddGroupMembersActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        button_change_group_name.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, ChangeWeeklyGroupNameActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        button_go_to_remove_weekly_group_member.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, RemoveWeeklyGroupMemberActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        button_go_to_change_member_contribution.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, ChangeWeeklyMemberContributionActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
                putExtra(EXTRA_MESSAGE_GROUP, group_number)
            }
            startActivity(intent)
        }
        button_see_total_member_contributions.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupActivity, TotalRoscaMemberContributionActivity::class.java).apply {
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