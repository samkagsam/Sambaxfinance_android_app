package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceAddGroupMembers
import com.sambaxfinance.sambax.api.ApiInterfaceTransferMoney
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.AddGroupMembersRequestModel
import com.sambaxfinance.sambax.models.AddGroupMembersResponseModel
import com.sambaxfinance.sambax.models.TransferMoneyRequestModel
import com.sambaxfinance.sambax.models.TransferMoneyResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

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


        //introduce variables from layout
        val button_send_money = findViewById<Button>(R.id.button_send_money)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumberRecipient)
        val transfer_money_given = findViewById<EditText>(R.id.et_money_to_send)


        button_send_money.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_send_money.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_send_money.isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            //val phone_number = phone_number_given.text.toString().toIntOrNull() ?: 0
            val phone_number_fresh = phone_number_given.text.toString().trim()
            val transfer_money = transfer_money_given.text.toString().toIntOrNull() ?: 0

            if(transfer_money == 0){
                transfer_money_given.error = "Amount to send is required"
                transfer_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(transfer_money)


            /*
            if(phone_number == 0){
                phone_number_given.error = "Amount to deposit is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }*/

            if(phone_number_fresh.isEmpty()){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            println(token)
            println(phone_number_fresh)



            //val transactionRequestModel = TransactionRequestModel(deposit_money)
            val transferMoneyRequestModel = TransferMoneyRequestModel(phone_number_fresh, transfer_money)

            val response = ServiceBuilder.buildService(ApiInterfaceTransferMoney::class.java)
            response.sendReq(transferMoneyRequestModel,"Bearer " + token).enqueue(
                object : Callback<TransferMoneyResponseModel> {
                    override fun onResponse(
                        call: Call<TransferMoneyResponseModel>,
                        response: Response<TransferMoneyResponseModel>
                    ) {
                        Toast.makeText(this@TransferActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        //println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tvResponseTransferMoney).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@TransferActivity, LandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@TransferActivity,"Error in processing transfer", Toast.LENGTH_LONG).show()

                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tvResponseTransferMoney).apply {
                                text = "Error:causes: either you don't have enough money or the person you are sending to is not registered with sambax finance app "
                            }

                        }
                    }

                    override fun onFailure(call: Call<TransferMoneyResponseModel>, t: Throwable) {
                        Toast.makeText(this@TransferActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvSaveResponse = findViewById<TextView>(R.id.tvResponseTransferMoney).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )
        }
    }
}