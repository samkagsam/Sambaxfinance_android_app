package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGroupMembers
import com.sambaxfinance.sambax.api.ApiInterfaceTotalRoscaMemberContribution
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TotalRoscaMemberContributionActivity : AppCompatActivity() {

    lateinit var myAdapterGroupMembers: MyAdapterTotalRoscaMemberContribution
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_rosca_member_contribution)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewTotalRoscaMemberContributionActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //let us initialize more variables
        val recyclerview_group_members = findViewById<RecyclerView>(R.id.recyclerview_total_rosca_member_contribution)
        recyclerview_group_members.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_group_members.layoutManager = linearLayoutManager

        val totalRoscaMemberContributionRequestModel = TotalRoscaMemberContributionRequestModel(group_identity_int)
        val response2 = ServiceBuilder.buildService(ApiInterfaceTotalRoscaMemberContribution::class.java)
        response2.sendReq(totalRoscaMemberContributionRequestModel,"Bearer " + token).enqueue(
            object : Callback<List<TotalRoscaMemberContributionResponseModel>> {
                override fun onResponse(
                    call: Call<List<TotalRoscaMemberContributionResponseModel>>,
                    response2: Response<List<TotalRoscaMemberContributionResponseModel>>
                ) {
                    Toast.makeText(this@TotalRoscaMemberContributionActivity,response2.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response2.message().toString())
                    println(response2.body().toString())
                    //println(response.body()?.get(1))
                    //println(response.body()?.get(0))

                    /*

                    val okResponse = response.message().toString()
                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.tvHello).apply {
                        text = "Hello "+ response.body()?.get(1)
                    }*/

                    //let us try to show results in recyclerview
                    val responseBody = response2.body()!!
                    println(responseBody)

                    if (responseBody.isEmpty()){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoMemberContributions).apply {
                            text = "Oops! There are no contributions this week!"
                        }
                    } else{
                        myAdapterGroupMembers = MyAdapterTotalRoscaMemberContribution(baseContext, responseBody)
                        myAdapterGroupMembers.notifyDataSetChanged()
                        recyclerview_group_members.adapter = myAdapterGroupMembers
                    }






                }

                override fun onFailure(call: Call<List<TotalRoscaMemberContributionResponseModel>>, t: Throwable) {
                    Toast.makeText(this@TotalRoscaMemberContributionActivity,t.toString(), Toast.LENGTH_LONG).show()
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