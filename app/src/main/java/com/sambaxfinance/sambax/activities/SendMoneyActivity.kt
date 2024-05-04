package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hbb20.CountryCodePicker
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceCurrencyExchange
import com.sambaxfinance.sambax.api.ApiInterfaceSendMoney
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.ExchangeRequestModel
import com.sambaxfinance.sambax.models.ExchangeResponseModel
import com.sambaxfinance.sambax.models.SendMoneyRequestModel
import com.sambaxfinance.sambax.models.SendMoneyResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendMoneyActivity : AppCompatActivity() {
    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_money)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewSendMoneyActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiate all the application fields
        val button_send_money = findViewById<Button>(R.id.button_send_money)
        val amount_given = findViewById<EditText>(R.id.etAmount)
        val receiver_name_given = findViewById<EditText>(R.id.etReceiverName)
        val receiver_phone_given = findViewById<EditText>(R.id.etReceiverPhoneNumber)

        val countryCodePicker = findViewById<CountryCodePicker>(R.id.countryCodePicker)

        // Assuming you have initialized your spinners in your Activity or Fragment
        val spinnerFromCurrency: Spinner = findViewById(R.id.spinnerFromCurrency)
        val spinnerReceiverMethod: Spinner = findViewById(R.id.spinnerReceiverMethod)

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

            // Get the selected item from the "From" spinner
            val selectedFromCurrency: String = spinnerFromCurrency.selectedItem as String

            // Get the selected item from the "Method" spinner
            val selectedReceiverMethod: String = spinnerReceiverMethod.selectedItem as String


            //let us get the form variables

            val amount_fresh = amount_given.text.toString().toIntOrNull() ?: 0
            val receiver_name_fresh = receiver_name_given.text.toString().trim()
            val receiver_phone_fresh = receiver_phone_given.text.toString().trim()

            // Move the retrieval of selectedCountryName and selectedCountryCode here
            val selectedCountryName: String = countryCodePicker.selectedCountryName

            if(amount_fresh == 0){
                amount_given.error = "amount is required"
                amount_given.requestFocus()
                return@setOnClickListener

            }
            if(receiver_name_fresh.isEmpty()){
                receiver_name_given.error = "name is required"
                receiver_name_given.requestFocus()
                return@setOnClickListener

            }
            if(receiver_phone_fresh.isEmpty()){
                receiver_phone_given.error = "phone number is required"
                receiver_phone_given.requestFocus()
                return@setOnClickListener

            }

            println(selectedFromCurrency)
            println(selectedReceiverMethod)

            println(amount_fresh)

            //DummyModel
            val sendMoneyRequestModel = SendMoneyRequestModel(selectedFromCurrency,amount_fresh, receiver_name_fresh, receiver_phone_fresh, selectedCountryName, selectedReceiverMethod
            )



            val response = ServiceBuilder.buildService(ApiInterfaceSendMoney::class.java)
            response.sendReq(sendMoneyRequestModel,"Bearer " + token).enqueue(
                object : Callback<SendMoneyResponseModel> {
                    override fun onResponse(
                        call: Call<SendMoneyResponseModel>,
                        response: Response<SendMoneyResponseModel>
                    ) {
                        Toast.makeText(this@SendMoneyActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())



                        val okResponse = response.message().toString()
                        if (okResponse == "OK"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@SendMoneyActivity, WalletActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@SendMoneyActivity,"You have no active loan to pay", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = response.message().toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SendMoneyResponseModel>, t: Throwable) {
                        Toast.makeText(this@SendMoneyActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                        //show error in error field
                        val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                            text = "Please check your internet connection"
                        }
                    }

                }
            )

        }
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