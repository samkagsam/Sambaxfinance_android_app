package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceChangeLtgName
import com.sambaxfinance.sambax.api.ApiInterfaceCreateLongTermGroup
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.CreateLongTermGroupRequestModel
import com.sambaxfinance.sambax.models.FDACreateResponseModel
import com.sambaxfinance.sambax.models.GroupNameChangeRequestModel
import com.sambaxfinance.sambax.models.GroupNameChangeResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeLtgNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_ltg_name)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val group_identity = intent.getStringExtra(EXTRA_MESSAGE_GROUP)
        val group_identity_int = group_identity!!.toInt()

        //introduce variables from layout
        val button_submit_new_ltg_name = findViewById<Button>(R.id.button_submit_new_ltg_name)

        val group_name_given = findViewById<EditText>(R.id.et_enter_new_ltg_name)


        button_submit_new_ltg_name.setOnClickListener {

            val group_name_to_use = group_name_given.text.toString().trim()




            if(group_name_to_use.isEmpty()){
                group_name_given.error = "group name is required"
                group_name_given.requestFocus()
                return@setOnClickListener

            }

            println(token)


            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val groupNameChangeRequestModel = GroupNameChangeRequestModel(group_identity_int, group_name_to_use)

            val response = ServiceBuilder.buildService(ApiInterfaceChangeLtgName::class.java)
            response.sendReq(groupNameChangeRequestModel, "Bearer " + token).enqueue(
                object : Callback<GroupNameChangeResponseModel> {
                    override fun onResponse(
                        call: Call<GroupNameChangeResponseModel>,
                        response: Response<GroupNameChangeResponseModel>
                    ) {
                        Toast.makeText(
                            this@ChangeLtgNameActivity,
                            response.message().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.successfully_updated)


                        val okResponse = response.message().toString()
                        println(okResponse)

                        if (okResponse == "Created") {
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tvChangeLtgNameErrorResponse).apply {
                                    text = ""
                                }

                            //start a new activity here
                            val intent =
                                Intent(this@ChangeLtgNameActivity, LongTermGroupLandingActivity::class.java).apply {
                                    putExtra(EXTRA_MESSAGE, token)
                                    //putExtra(EXTRA_MESSAGE_GROUP, response.body()?.id.toString())
                                    putExtra(EXTRA_MESSAGE_GROUP, group_identity)

                                }
                            startActivity(intent)
                        } else {
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(
                                this@ChangeLtgNameActivity,
                                "Error in changing group name",
                                Toast.LENGTH_LONG
                            ).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse =
                                findViewById<TextView>(R.id.tvChangeLtgNameErrorResponse).apply {
                                    text = "There was an error in changing the name of your long term group"
                                }
                        }
                    }

                    override fun onFailure(call: Call<GroupNameChangeResponseModel>, t: Throwable) {
                        Toast.makeText(this@ChangeLtgNameActivity, t.toString(), Toast.LENGTH_LONG)
                            .show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse =
                            findViewById<TextView>(R.id.tvChangeLtgNameErrorResponse).apply {
                                text = "Please check your internet connection"
                            }
                    }

                }
            )
        }
    }
}