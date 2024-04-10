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
import com.sambaxfinance.sambax.api.ApiInterfaceQarAddMoneyByCard
import com.sambaxfinance.sambax.api.ApiInterfaceUgXAddMoneyByCard
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.AddMoneyByCardRequestModel
import com.sambaxfinance.sambax.models.AddMoneyByCardResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QarAddMoneyByCardActivity : AppCompatActivity() {

    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler() // Initialize the Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qar_add_money_by_card)

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

        val cardholderName = findViewById<EditText>(R.id.editTextCardholderName).text.toString()
        val cardNumber = findViewById<EditText>(R.id.editTextCardNumber).text.toString()
        val expiryDate = findViewById<EditText>(R.id.editTextExpiryDate).text.toString()
        val cvv = findViewById<EditText>(R.id.editTextCVV).text.toString()

        val buttonAddMoney = findViewById<Button>(R.id.buttonAddMoney)
        val deposit_money_given = findViewById<EditText>(R.id.editTextAmount)

        buttonAddMoney.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonAddMoney.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonAddMoney.isEnabled = true
            }, 30000) // 30 seconds in milliseconds


            val deposit_money = deposit_money_given.text.toString().toIntOrNull() ?: 0

            if(deposit_money == 0){
                deposit_money_given.error = "Amount to add is required"
                deposit_money_given.requestFocus()
                return@setOnClickListener

            }
            println(token)
            println(deposit_money)

            val errorMessageTextView = findViewById<TextView>(R.id.tvResponse)

            // Validate Cardholder Name (ensure it's not empty)
            if (cardholderName.isEmpty()) {
                // Show error message or handle the case where cardholder name is empty
                errorMessageTextView.text = "Card holder name is required"
            }
            // Validate CVV (ensure it's not empty and of correct length)
            if (cvv.isEmpty() || cvv.length != 3) {
                // Show error message or handle the case where CVV is invalid
                errorMessageTextView.text = "CVV is required"
            }

            // Validate Card Number
            if (cardNumber.isEmpty() || !isValidCardNumber(cardNumber)) {
                errorMessageTextView.text = "Invalid card number"
                errorMessageTextView.visibility = View.VISIBLE
            } else if (expiryDate.isEmpty() || !isValidExpiryDate(expiryDate)) {
                errorMessageTextView.text = "Invalid expiry date"
                errorMessageTextView.visibility = View.VISIBLE
            } else {
                errorMessageTextView.visibility = View.GONE // Hide error message if validation passes
            }




            val addMoneyByCardRequestModel = AddMoneyByCardRequestModel(cardholderName,cardNumber,cvv,expiryDate,deposit_money)

            val response = ServiceBuilder.buildService(ApiInterfaceQarAddMoneyByCard::class.java)
            response.sendReq(addMoneyByCardRequestModel,"Bearer " + token).enqueue(
                object : Callback<AddMoneyByCardResponseModel> {
                    override fun onResponse(
                        call: Call<AddMoneyByCardResponseModel>,
                        response: Response<AddMoneyByCardResponseModel>
                    ) {
                        Toast.makeText(this@QarAddMoneyByCardActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.amount)
                        println(response.body()?.created_at)

                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //let us first clear all error codes
                            // Capture the layout's TextView and set the string as its text
                            val tvSaveResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = ""
                            }

                            //start a new activity here
                            val intent = Intent(this@QarAddMoneyByCardActivity, WalletActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            Toast.makeText(this@QarAddMoneyByCardActivity,"Error in processing deposit", Toast.LENGTH_LONG).show()
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = response.message().toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<AddMoneyByCardResponseModel>, t: Throwable) {
                        Toast.makeText(this@QarAddMoneyByCardActivity,t.toString(), Toast.LENGTH_LONG).show()
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
    // Validation logic for card number format
    fun isValidCardNumber(cardNumber: String): Boolean {
        // Remove all non-digit characters from the card number
        val cleanCardNumber = cardNumber.replace("\\s".toRegex(), "")

        // Check if the card number is 16 digits long
        if (cleanCardNumber.length != 16) {
            return false
        }

        // Perform Luhn Algorithm validation
        var sum = 0
        for (i in cleanCardNumber.indices) {
            var digit = cleanCardNumber[i] - '0'
            if (i % 2 == 0) {
                digit *= 2
                if (digit > 9) {
                    digit -= 9
                }
            }
            sum += digit
        }
        return sum % 10 == 0
    }

    // Validation logic for expiry date format
    fun isValidExpiryDate(expiryDate: String): Boolean {
        // Check if the expiry date has the correct format (MM/YYYY)
        val regex = "^(0[1-9]|1[0-2])\\/(\\d{4})$".toRegex()
        if (!expiryDate.matches(regex)) {
            return false
        }

        // Extract month and year from the expiry date string
        val (monthStr, yearStr) = expiryDate.split("/")
        val month = monthStr.toInt()
        val year = yearStr.toInt()

        // Check if the expiry date is not in the past
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100 // Get last two digits of the current year
        return if (year > currentYear) {
            true
        } else if (year == currentYear) {
            val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1 // January is 0
            month >= currentMonth
        } else {
            false
        }
    }
}