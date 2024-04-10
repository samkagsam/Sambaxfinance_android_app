package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceCurrencyExchange
import com.sambaxfinance.sambax.api.ApiInterfaceLoanApply
import com.sambaxfinance.sambax.api.ApiInterfacePayment
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyExchangeActivity : AppCompatActivity() {
    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_exchange)

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

        //let us initiate all the application fields
        val button_exchange_currency = findViewById<Button>(R.id.button_exchange_currency)
        val amount_given = findViewById<EditText>(R.id.etAmount)
        // Assuming you have initialized your spinners in your Activity or Fragment
        val spinnerFromCurrency: Spinner = findViewById(R.id.spinnerFromCurrency)
        val spinnerToCurrency: Spinner = findViewById(R.id.spinnerToCurrency)

        button_exchange_currency.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            button_exchange_currency.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                button_exchange_currency.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            // Get the selected item from the "From" spinner
            val selectedFromCurrency: String = spinnerFromCurrency.selectedItem as String

            // Get the selected item from the "To" spinner
            val selectedToCurrency: String = spinnerToCurrency.selectedItem as String


            //let us get the form variables

            val amount_fresh = amount_given.text.toString().toIntOrNull() ?: 0



            if(amount_fresh == 0){
                amount_given.error = "loan amount is required"
                amount_given.requestFocus()
                return@setOnClickListener

            }




            println(selectedFromCurrency)
            println(selectedToCurrency)

            println(amount_fresh)

            //DummyModel
            val exchangeRequestModel = ExchangeRequestModel(selectedFromCurrency,selectedToCurrency,amount_fresh
            )



            val response = ServiceBuilder.buildService(ApiInterfaceCurrencyExchange::class.java)
            response.sendReq(exchangeRequestModel,"Bearer " + token).enqueue(
                object : Callback<ExchangeResponseModel> {
                    override fun onResponse(
                        call: Call<ExchangeResponseModel>,
                        response: Response<ExchangeResponseModel>
                    ) {
                        Toast.makeText(this@CurrencyExchangeActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.amount)


                        val okResponse = response.message().toString()
                        if (okResponse == "OK"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@CurrencyExchangeActivity, NewLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@CurrencyExchangeActivity,"You have no active loan to pay", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = response.message().toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ExchangeResponseModel>, t: Throwable) {
                        Toast.makeText(this@CurrencyExchangeActivity,t.toString(), Toast.LENGTH_LONG).show()
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
}