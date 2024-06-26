package com.sambaxfinance.sambax.activities

import android.content.Intent
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
import com.sambaxfinance.sambax.api.ApiInterfaceGeneralGroupLanding
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GeneralGroupLandingResponseModel
import com.sambaxfinance.sambax.models.MyAdapterUserGroups
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RotatingSavingGroupsActivity : AppCompatActivity() {

    lateinit var myAdapterUserGroups: MyAdapterUserGroups

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotating_saving_groups)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewRotatingSavingGroupsActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initialize more variables
        val recyclerview_user_groups = findViewById<RecyclerView>(R.id.rvUserSavingGroups)
        recyclerview_user_groups.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_user_groups.layoutManager = linearLayoutManager



        val response2 = ServiceBuilder.buildService(ApiInterfaceGeneralGroupLanding::class.java)
        response2.sendReq("Bearer " + token).enqueue(
            object : Callback<List<GeneralGroupLandingResponseModel>> {
                override fun onResponse(
                    call: Call<List<GeneralGroupLandingResponseModel>>,
                    response2: Response<List<GeneralGroupLandingResponseModel>>
                ) {


                    Toast.makeText(this@RotatingSavingGroupsActivity,response2.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response2.message().toString())
                    println(response2.body().toString())

                    val response_message_string = response2.message().toString()

                    if (response_message_string == "Forbidden"){
                        println("not allowed")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.akunaSavingGroup).apply {
                            text = "Oops! You do not belong to any saving group!"
                        }
                    } else{
                        //let us try to show results in recyclerview
                        val responseBody = response2.body()!!
                        println(responseBody)

                        if (responseBody.isEmpty()){
                            println("List is empty")
                            // Capture the layout's TextView and set the string as its text
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.akunaSavingGroup).apply {
                                text = "Oops! You do not belong to any saving group!"
                            }
                        } else{
                            myAdapterUserGroups = MyAdapterUserGroups(baseContext, responseBody)
                            myAdapterUserGroups.notifyDataSetChanged()
                            recyclerview_user_groups.adapter = myAdapterUserGroups

                            myAdapterUserGroups.onItemClick = {
                                val intent = Intent(this@RotatingSavingGroupsActivity, GroupActivity:: class.java)
                                intent.putExtra("group_credentials", it)
                                intent.putExtra(EXTRA_MESSAGE, token)
                                startActivity(intent)
                            }
                        }
                    }



                }

                override fun onFailure(call: Call<List<GeneralGroupLandingResponseModel>>, t: Throwable) {
                    Toast.makeText(this@RotatingSavingGroupsActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
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