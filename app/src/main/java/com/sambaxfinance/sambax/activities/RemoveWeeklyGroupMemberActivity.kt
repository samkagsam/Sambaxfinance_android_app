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
import com.sambaxfinance.sambax.api.ApiInterfaceAddGroupMembers
import com.sambaxfinance.sambax.api.ApiInterfaceRemoveWeeklyGroupMember
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.AddGroupMembersRequestModel
import com.sambaxfinance.sambax.models.AddGroupMembersResponseModel
import com.sambaxfinance.sambax.models.RemoveGroupMemberResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoveWeeklyGroupMemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_weekly_group_member)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewRemoveWeeklyGroupMemberActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout
        val buttonSendPhoneNumber = findViewById<Button>(R.id.button_remove_weekly_group_member)
        val phone_number_given = findViewById<EditText>(R.id.et_phone_number_of_group_member_to_remove)


        buttonSendPhoneNumber.setOnClickListener {
            val phone_number = phone_number_given.text.toString().toIntOrNull() ?: 0


            if(phone_number == 0){
                phone_number_given.error = "Amount to deposit is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(phone_number)



            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val addGroupMembersRequestModel = AddGroupMembersRequestModel(group_identity_int, phone_number)

            val response = ServiceBuilder.buildService(ApiInterfaceRemoveWeeklyGroupMember::class.java)
            response.sendReq(addGroupMembersRequestModel,"Bearer " + token).enqueue(
                object : Callback<RemoveGroupMemberResponseModel> {
                    override fun onResponse(
                        call: Call<RemoveGroupMemberResponseModel>,
                        response: Response<RemoveGroupMemberResponseModel>
                    ) {
                        Toast.makeText(this@RemoveWeeklyGroupMemberActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        //println(response.body()?.amount)
                        println(response.body()?.member_removed)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tv_response_phone_number_of_group_member_to_remove).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@RemoveWeeklyGroupMemberActivity, GroupActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                                putExtra(EXTRA_MESSAGE_GROUP, group_identity_int.toString())
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@RemoveWeeklyGroupMemberActivity,"Error in processing member removal", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            /*val tvSaveResponse = findViewById<TextView>(R.id.tvGroupDepositResponse).apply {
                                text = "There was an error in processing your group join invitation"
                            }*/
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tv_response_phone_number_of_group_member_to_remove).apply {
                                text = "Forbidden to remove member"
                            }

                        }
                    }

                    override fun onFailure(call: Call<RemoveGroupMemberResponseModel>, t: Throwable) {
                        Toast.makeText(this@RemoveWeeklyGroupMemberActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse = findViewById<TextView>(R.id.tv_response_phone_number_of_group_member_to_remove).apply {
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