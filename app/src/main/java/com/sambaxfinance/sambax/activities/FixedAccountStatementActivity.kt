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
import com.sambaxfinance.sambax.api.ApiInterfaceFDAStatement
import com.sambaxfinance.sambax.api.ApiInterfaceStatement
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.MyAdapterStatement
import com.sambaxfinance.sambax.models.StatementResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixedAccountStatementActivity : AppCompatActivity() {

    lateinit var myAdapterStatement: MyAdapterStatement
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_account_statement)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewFixedAccountStatementActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initialize variables
        val recyclerview_statement = findViewById<RecyclerView>(R.id.recyclerview_fda_statement)
        recyclerview_statement.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_statement.layoutManager = linearLayoutManager


        // Get the Intent that started this activity and extract the string
        //val token = intent.getStringExtra(EXTRA_MESSAGE)

        val response = ServiceBuilder.buildService(ApiInterfaceFDAStatement::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<List<StatementResponseModel>> {
                override fun onResponse(
                    call: Call<List<StatementResponseModel>>,
                    response: Response<List<StatementResponseModel>>
                ) {
                    Toast.makeText(this@FixedAccountStatementActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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
                    val responseBody = response.body()!!
                    println(responseBody)

                    if (responseBody.isEmpty()){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyResponse = findViewById<TextView>(R.id.tvSeeEmptyStatement).apply {
                            text = "Oops! You do not have any transactions on your fixed deposit account!"
                        }
                    } else{
                        myAdapterStatement = MyAdapterStatement(baseContext, responseBody)
                        myAdapterStatement.notifyDataSetChanged()
                        recyclerview_statement.adapter = myAdapterStatement
                    }






                }

                override fun onFailure(call: Call<List<StatementResponseModel>>, t: Throwable) {
                    Toast.makeText(this@FixedAccountStatementActivity,t.toString(), Toast.LENGTH_LONG).show()
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