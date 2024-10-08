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
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGetTotalGroupRequests
import com.sambaxfinance.sambax.api.ApiInterfaceGetTotalLongTermGroupRequests
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.GetTotalRequestsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupTypeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_type)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewGroupTypeActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us show requests
        getTotalWeeklyGroupRequests(token.toString())
        getTotalLongTermGroupRequests(token.toString())


        //introduce variables from layout
        val btn_weekly_saving_groups = findViewById<Button>(R.id.btn_weekly_saving_groups)
        val btn_long_term_saving_groups = findViewById<Button>(R.id.btn_long_term_saving_groups)
        val btnHowGroupSavingsWork = findViewById<Button>(R.id.btnHowGroupSavingsWork)

        btn_weekly_saving_groups.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupTypeActivity, GroupActivityLanding::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_long_term_saving_groups.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupTypeActivity, LongTermSavingGroupsGeneralActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

        btnHowGroupSavingsWork.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@GroupTypeActivity, HowGroupsWorkActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        // Handle action bar item clicks here.
        ActionBarUtils.handleActionBarItemClick(this, item, token)

        // Add more cases for other menu items if needed

        return super.onOptionsItemSelected(item)

    }
}