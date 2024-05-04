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
import com.sambaxfinance.sambax.api.ApiInterfaceGroupCreate
import com.sambaxfinance.sambax.api.ApiInterfaceGroupDeposit
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GroupCreateRequestModel
import com.sambaxfinance.sambax.models.GroupCreateResponseModel
import com.sambaxfinance.sambax.models.GroupDepositRequestModel
import com.sambaxfinance.sambax.models.GroupDepositResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewCreateGroupActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }


        //val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout
        val buttonCreateSavingGroup = findViewById<Button>(R.id.buttonCreateSavingGroup)
        val payout_money_given = findViewById<EditText>(R.id.etGroupPayoutAmount)
        val group_name_given = findViewById<EditText>(R.id.et_enter_group_name)


        buttonCreateSavingGroup.setOnClickListener {
            val payout_money = payout_money_given.text.toString().toIntOrNull() ?: 0
            val group_name_to_use = group_name_given.text.toString().trim()


            if (payout_money == 0) {
                payout_money_given.error = "Amount to save is required"
                payout_money_given.requestFocus()
                return@setOnClickListener

            }

            if(group_name_to_use.isEmpty()){
                group_name_given.error = "group name is required"
                group_name_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(payout_money)

            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val groupCreateRequestModel = GroupCreateRequestModel(group_name_to_use, payout_money)

            val response = ServiceBuilder.buildService(ApiInterfaceGroupCreate::class.java)
            response.sendReq(groupCreateRequestModel, "Bearer " + token).enqueue(
                object : Callback<GroupCreateResponseModel> {
                    override fun onResponse(
                        call: Call<GroupCreateResponseModel>,
                        response: Response<GroupCreateResponseModel>
                    ) {
                        Toast.makeText(
                            this@CreateGroupActivity,
                            response.message().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.id)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created") {
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tvGroupCreateResponse).apply {
                                    text = ""
                                }

                            //start a new activity here
                            val intent =
                                Intent(this@CreateGroupActivity, GroupActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)
                                    putExtra(EXTRA_MESSAGE_GROUP, response.body()?.id.toString())
                                }
                            startActivity(intent)
                        } else {
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(
                                this@CreateGroupActivity,
                                "Error in creating group",
                                Toast.LENGTH_LONG
                            ).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tvGroupCreateResponse).apply {
                                    text = "There was an error in creating your saving group"
                                }
                        }
                    }

                    override fun onFailure(call: Call<GroupCreateResponseModel>, t: Throwable) {
                        Toast.makeText(this@CreateGroupActivity, t.toString(), Toast.LENGTH_LONG)
                            .show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse =
                            findViewById<TextView>(R.id.tvGroupCreateResponse).apply {
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