package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLoanApply
import com.sambaxfinance.sambax.api.ServiceBuilder
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hbb20.CountryCodePicker
import com.sambaxfinance.sambax.api.ApiInterface
import com.sambaxfinance.sambax.models.*
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoanApplyActivity : AppCompatActivity() {
    private var isButtonEnabled = true // Variable to track button state
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_apply)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewLoanApplyActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiate all the application fields
        val buttonSubmitApplication = findViewById<Button>(R.id.buttonSubmitApplication)
        val first_name_given = findViewById<EditText>(R.id.etFirstName)
        val last_name_given = findViewById<EditText>(R.id.etLastName)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumber)
        val email_given = findViewById<EditText>(R.id.etYourEmail)
        val loan_amount_given = findViewById<EditText>(R.id.etLoanAmount)
        val town_given = findViewById<EditText>(R.id.etTown)
        val district_given = findViewById<EditText>(R.id.etDistrict)

        val countryCodePicker = findViewById<CountryCodePicker>(R.id.countryCodePicker)


        buttonSubmitApplication.setOnClickListener {
            if (!isButtonEnabled) {
                return@setOnClickListener // Prevent double-clicking
            }

            // Disable the button
            isButtonEnabled = false
            buttonSubmitApplication.isEnabled = false

            // Enable the button after 30 seconds
            handler.postDelayed({
                isButtonEnabled = true
                buttonSubmitApplication.isEnabled = true
            }, 30000) // 30 seconds in milliseconds

            // Move the retrieval of selectedCountryName and selectedCountryCode here
            val selectedCountryName: String = countryCodePicker.selectedCountryName
            val selectedCountryCode: String = countryCodePicker.selectedCountryCode


            //let us get the form variables
            val first_name_fresh = first_name_given.text.toString().trim()
            val last_name_fresh = last_name_given.text.toString().trim()
            val phone_number_fresh = phone_number_given.text.toString().trim()
            val email_fresh = email_given.text.toString().trim()
            val loan_amount_fresh = loan_amount_given.text.toString().toIntOrNull() ?: 0
            val town_fresh = town_given.text.toString().trim()
            val district_fresh = district_given.text.toString().trim()
            //val phone_number_fresh = phone_number_given.text.toString().toIntOrNull() ?: 0
            //val phone_number_fresh = "0"




            if(first_name_fresh.isEmpty()){
                first_name_given.error = "first name is required"
                first_name_given.requestFocus()
                return@setOnClickListener

            }

            if(last_name_fresh.isEmpty()){
                last_name_given.error = "last name is required"
                last_name_given.requestFocus()
                return@setOnClickListener

            }

            if(phone_number_fresh.isEmpty()){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }
            if(email_fresh.isEmpty()){
                email_given.error = "email address is required"
                email_given.requestFocus()
                return@setOnClickListener

            }

            if(loan_amount_fresh == 0){
                loan_amount_given.error = "loan amount is required"
                loan_amount_given.requestFocus()
                return@setOnClickListener

            }
            if(town_fresh.isEmpty()){
                town_given.error = "town is required"
                town_given.requestFocus()
                return@setOnClickListener

            }
            if(district_fresh.isEmpty()){
                district_given.error = "district is required"
                district_given.requestFocus()
                return@setOnClickListener

            }

            /*
            println(first_name_fresh)
            println(last_name_fresh)
            println(phone_number_fresh)
            println(email_fresh)
            println(loan_amount_fresh)
            println(town_fresh)
            println(district_fresh)
            println(selectedCountryName)
            println(selectedCountryCode)

             */

            //DummyModel
            val requestModel = ApplyRequestModel(first_name_fresh,last_name_fresh,phone_number_fresh,email_fresh,loan_amount_fresh, town_fresh, district_fresh, selectedCountryName
            )

            val response = ServiceBuilder.buildService(ApiInterfaceLoanApply::class.java)
            response.sendReq(requestModel).enqueue(
                object : Callback<ApplyResponseModel> {
                    override fun onResponse(
                        call: Call<ApplyResponseModel>,
                        response: Response<ApplyResponseModel>
                    ) {
                        Toast.makeText(this@LoanApplyActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.access_token)
                        //println(response.body()?.token_type)



                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")

                            //let us first clear the error field
                            // Capture the layout's TextView and set the string as its text

                            //startActivity(intent)
                            val intent = Intent(this@LoanApplyActivity, LoansActivity::class.java).apply {
                                //putExtra(EXTRA_MESSAGE_EMAIL_SIGN_OTP_TOKEN, signtoken)
                                putExtra(EXTRA_MESSAGE, token)

                            }
                            startActivity(intent)


                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                                text = response.message().toString()
                            }

                        }
                    }

                    override fun onFailure(call: Call<ApplyResponseModel>, t: Throwable) {
                        Toast.makeText(this@LoanApplyActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvResponse = findViewById<TextView>(R.id.tvResponse).apply {
                            text = "please check your internet connection"
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