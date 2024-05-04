package com.sambaxfinance.sambax.activities

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceGroupStatement
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupStatementActivity : AppCompatActivity() {

    lateinit var myAdapterShowGroupStatement: MyAdapterShowGroupStatement
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_statement)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewGroupStatementActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initialize variables
        val recyclerview_statement = findViewById<RecyclerView>(R.id.recyclerview_show_group_statement)
        recyclerview_statement.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_statement.layoutManager = linearLayoutManager


        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()
        //println(group_identity_int)

        //introduce variables from layout

        val groupStatementRequestModel = GroupStatementRequestModel( group_identity_int)

        val response = ServiceBuilder.buildService(ApiInterfaceGroupStatement::class.java)
        response.sendReq(groupStatementRequestModel,"Bearer " + token).enqueue(
            object : Callback<List<GroupStatementResponseModel>> {
                override fun onResponse(
                    call: Call<List<GroupStatementResponseModel>>,
                    response: Response<List<GroupStatementResponseModel>>
                ) {
                    Toast.makeText(this@GroupStatementActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    println("we were successful")
                    println(response.message().toString())
                    println(response.body().toString())
                    //println(response.body()?.get(1))
                    //println(response.body()?.get(0))

                    /*

                    val okResponse = response.message().toString()
                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.tvHello).apply {
                        text = "Hello "+ response.body()?.get(1)
                    }*/

                    //let us try to show results in recyclerview

                    val okResponse = response.message().toString()
                    println(okResponse)

                    if(okResponse == "Forbidden"){
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroupStatement).apply {
                            text = "Oops! You do not have any group transactions for the current week and cycle!"
                        }

                    } else{
                        val responseBody = response.body()!!
                        //println(responseBody)
                        if(responseBody.isEmpty()){
                            println("List is empty")
                            // Capture the layout's TextView and set the string as its text
                            val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroupStatement).apply {
                                text = "Oops! You do not have any group transactions for the current week and cycle!"
                            }
                        }else{
                            myAdapterShowGroupStatement = MyAdapterShowGroupStatement(baseContext, responseBody)
                            myAdapterShowGroupStatement.notifyDataSetChanged()
                            recyclerview_statement.adapter = myAdapterShowGroupStatement
                        }

                    }







                }

                override fun onFailure(call: Call<List<GroupStatementResponseModel>>, t: Throwable) {
                    Toast.makeText(this@GroupStatementActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                    // Capture the layout's TextView and set the string as its text
                    val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvNoGroupStatement).apply {
                        text = "Oops! Please check your internet connection!"
                    }
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