package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGetTotalLongTermGroupRequests
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GetTotalRequestsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LongTermSavingGroupsGeneralActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_term_saving_groups_general)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLongTermSavingGroupsGeneralActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us show requests
        //getTotalWeeklyGroupRequests(token.toString())
        getTotalLongTermGroupRequests(token.toString())



        //let us initiate all the buttons
        val btn_go_to_create_LTG = findViewById<Button>(R.id.btn_go_to_create_LTG)
        val btn_see_long_term_group_requests = findViewById<Button>(R.id.btn_see_long_term_group_requests)
        val btn_my_long_term_groups = findViewById<Button>(R.id.btn_my_long_term_groups)





        btn_go_to_create_LTG.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermSavingGroupsGeneralActivity, CreateLongTermGroupActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_see_long_term_group_requests.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermSavingGroupsGeneralActivity, LongTermGroupRequestsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_my_long_term_groups.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@LongTermSavingGroupsGeneralActivity, LongTermSavingGroupsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
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