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
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeeLoansActivity : AppCompatActivity() {

    lateinit var myAdapterSeeLoans: MyAdapterSeeLoans
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_loans)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewGroupRequestsActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initialize more variables
        val recyclerview_see_loans = findViewById<RecyclerView>(R.id.rvSeeLoans)
        recyclerview_see_loans.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_see_loans.layoutManager = linearLayoutManager



        val response = ServiceBuilder.buildService(ApiInterfaceSeeLoans::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<List<SeeLoansResponseModel>> {
                override fun onResponse(
                    call: Call<List<SeeLoansResponseModel>>,
                    response: Response<List<SeeLoansResponseModel>>
                ) {
                    Toast.makeText(this@SeeLoansActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())

                    val response_message = response.message().toString()

                    if (response_message == "Forbidden"){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvAkunaGroupRequests).apply {
                            text = "Oops! You do not have any Loans!"
                        }
                    } else{
                        //let us try to show results in recyclerview
                        val responseBody = response.body()!!
                        println(responseBody)

                        if (responseBody.isEmpty()){
                            println("List is empty")
                            // Capture the layout's TextView and set the string as its text
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvAkunaGroupRequests).apply {
                                text = "Oops! You do not have any loans!"
                            }
                        } else{
                            myAdapterSeeLoans = MyAdapterSeeLoans(baseContext, responseBody)
                            myAdapterSeeLoans.notifyDataSetChanged()
                            recyclerview_see_loans.adapter = myAdapterSeeLoans

                            myAdapterSeeLoans.onItemClick = {

                                /*val intent = Intent(this@GroupRequestsActivity, GroupActivity:: class.java)
                                intent.putExtra("group_credentials", it)
                                intent.putExtra(EXTRA_MESSAGE, token)
                                startActivity(intent)*/

                                val request_id = it.loan_id
                                println("kugeza")
                                println(request_id)

                                //start a new activity here
                                val intent = Intent(this@SeeLoansActivity, LoanStatementActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)
                                    //putExtra(EXTRA_MESSAGE_GROUP, it.loan_id)
                                    putExtra(EXTRA_MESSAGE_GROUP, request_id.toString())
                                }
                                startActivity(intent)

                            }


                        }
                    }


                }

                override fun onFailure(call: Call<List<SeeLoansResponseModel>>, t: Throwable) {
                    Toast.makeText(this@SeeLoansActivity,t.toString(), Toast.LENGTH_LONG).show()
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