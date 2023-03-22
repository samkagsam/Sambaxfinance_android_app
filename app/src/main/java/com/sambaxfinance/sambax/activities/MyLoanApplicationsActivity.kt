package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceMyLoanApplications
//import com.example.sambax.api.ApiInterfaceMyLoanApplications
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.MyAdapter
import com.sambaxfinance.sambax.models.MyLoanApplicationsResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLoanApplicationsActivity : AppCompatActivity() {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_loan_applications)


        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        //let us initialize variables
        val recyclerview_applications = findViewById<RecyclerView>(R.id.recyclerview_applications)
        recyclerview_applications.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerview_applications.layoutManager = linearLayoutManager


        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        val response = ServiceBuilder.buildService(ApiInterfaceMyLoanApplications::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<List<MyLoanApplicationsResponseModel>> {
                override fun onResponse(
                    call: Call<List<MyLoanApplicationsResponseModel>>,
                    response: Response<List<MyLoanApplicationsResponseModel>>
                ) {
                    Toast.makeText(this@MyLoanApplicationsActivity,response.message().toString(), Toast.LENGTH_LONG).show()
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

                    if(responseBody.isEmpty()){
                        println("List is empty")
                        // Capture the layout's TextView and set the string as its text
                        val tvSeeEmptyApplications = findViewById<TextView>(R.id.tvSeeEmptyApplications).apply {
                            text = "Oops! You have not made any loan applications!"
                        }
                    }else{
                        myAdapter = MyAdapter(baseContext, responseBody)
                        myAdapter.notifyDataSetChanged()
                        recyclerview_applications.adapter = myAdapter
                    }






                }

                override fun onFailure(call: Call<List<MyLoanApplicationsResponseModel>>, t: Throwable) {
                    Toast.makeText(this@MyLoanApplicationsActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )
    }
}