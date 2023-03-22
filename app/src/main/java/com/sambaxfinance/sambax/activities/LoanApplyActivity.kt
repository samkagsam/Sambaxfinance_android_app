package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLoanApply
import com.sambaxfinance.sambax.api.ServiceBuilder
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.models.*
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoanApplyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_apply)

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
        //val first_name_given = findViewById<EditText>(R.id.etFirstNameApply)
        //val middle_name_given = findViewById<EditText>(R.id.etMiddleNameApply)
        //val last_name_given = findViewById<EditText>(R.id.etLastNameApply)
        val gender_given = findViewById<EditText>(R.id.etGender)
        val date_of_birth_given = findViewById<EditText>(R.id.etBirth)
        val home_address_given = findViewById<EditText>(R.id.etHomeAddress)
        val work_address_given = findViewById<EditText>(R.id.etWorkAddress)
        val nature_of_business_given = findViewById<EditText>(R.id.etNatureOfWork)
        //val contact_one_given = findViewById<EditText>(R.id.etContactOne)
        //val contact_two_given = findViewById<EditText>(R.id.etContactTwo)
        val requested_loan_amount_given = findViewById<EditText>(R.id.etLoanAmount)
        val guarantor_one_given = findViewById<EditText>(R.id.etGuarantorOne)
        val guarantor_two_given = findViewById<EditText>(R.id.etGuarantorTwo)
        val guarantor_one_contact_given = findViewById<EditText>(R.id.etGuarantorOneContact)
        val guarantor_two_contact_given = findViewById<EditText>(R.id.etGuarantorTwoContact)
        val guarantor_two_relationship_given = findViewById<EditText>(R.id.etGuarantorTwoRelationship)
        val guarantor_one_relationship_given = findViewById<EditText>(R.id.etGuarantorOneRelationship)
        val purpose_for_loan_given = findViewById<EditText>(R.id.etLoanPurpose)

        val buttonSendApplication = findViewById<Button>(R.id.buttonSendApplication)

        buttonSendApplication.setOnClickListener {
            //let us upload the images here
            //uploadImage()

            //val first_name = first_name_given.text.toString().trim()
            //val middle_name = middle_name_given.text.toString().trim()
            //val last_name = last_name_given.text.toString().trim()
            val gender = gender_given.text.toString().trim()
            val date_of_birth = date_of_birth_given.text.toString().trim()
            val home_address = home_address_given.text.toString().trim()
            val work_address = work_address_given.text.toString().trim()
            val nature_of_business = nature_of_business_given.text.toString().trim()
            //val contact_one = contact_one_given.text.toString().trim().toIntOrNull() ?: 0
            //val contact_two = contact_two_given.text.toString().trim().toIntOrNull() ?: 0
            val requested_loan_amount = requested_loan_amount_given.text.toString().trim().toIntOrNull() ?: 0
            val guarantor_one = guarantor_one_given.text.toString().trim()
            val guarantor_two = guarantor_two_given.text.toString().trim()
            val guarantor_one_contact = guarantor_one_contact_given.text.toString().trim().toIntOrNull() ?: 0
            val guarantor_two_contact = guarantor_two_contact_given.text.toString().trim().toIntOrNull() ?: 0
            val guarantor_two_relationship = guarantor_two_relationship_given.text.toString().trim()
            val guarantor_one_relationship = guarantor_one_relationship_given.text.toString().trim()
            val purpose_for_loan = purpose_for_loan_given.text.toString().trim()

            /*if(first_name.isEmpty()){
                first_name_given.error = " is required"
                first_name_given.requestFocus()
                return@setOnClickListener

            }
            if(last_name.isEmpty()){
                last_name_given.error = " is required"
                last_name_given.requestFocus()
                return@setOnClickListener

            }*/
            if(gender.isEmpty()){
                gender_given.error = " is required"
                gender_given.requestFocus()
                return@setOnClickListener

            }
            if(date_of_birth.isEmpty()){
                date_of_birth_given.error = " is required"
                date_of_birth_given.requestFocus()
                return@setOnClickListener

            }
            if(home_address.isEmpty()){
                home_address_given.error = " is required"
                home_address_given.requestFocus()
                return@setOnClickListener

            }
            if(work_address.isEmpty()){
                work_address_given.error = " is required"
                work_address_given.requestFocus()
                return@setOnClickListener

            }
            if(nature_of_business.isEmpty()){
                nature_of_business_given.error = " is required"
                nature_of_business_given.requestFocus()
                return@setOnClickListener

            }
            /*
            if(contact_one == 0){
                contact_one_given.error = "phone number is required"
                contact_one_given.requestFocus()
                return@setOnClickListener

            }
            if(contact_two == 0){
                contact_two_given.error = "phone number is required"
                contact_two_given.requestFocus()
                return@setOnClickListener

            }*/

            if(requested_loan_amount == 0){
                requested_loan_amount_given.error = "phone number is required"
                requested_loan_amount_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_one.isEmpty()){
                guarantor_one_given.error = " is required"
                guarantor_one_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_two.isEmpty()){
                guarantor_two_given.error = " is required"
                guarantor_two_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_one_contact == 0){
                guarantor_one_contact_given.error = "phone number is required"
                guarantor_one_contact_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_two_contact == 0){
                guarantor_two_contact_given.error = "phone number is required"
                guarantor_two_contact_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_two_relationship.isEmpty()){
                guarantor_two_relationship_given.error = " is required"
                guarantor_two_relationship_given.requestFocus()
                return@setOnClickListener

            }
            if(guarantor_one_relationship.isEmpty()){
                guarantor_one_relationship_given.error = " is required"
                guarantor_one_relationship_given.requestFocus()
                return@setOnClickListener

            }
            if(purpose_for_loan.isEmpty()){
                purpose_for_loan_given.error = " is required"
                purpose_for_loan_given.requestFocus()
                return@setOnClickListener

            }



            val applyRequestModel = ApplyRequestModel("available","available","available", gender, date_of_birth, home_address, work_address, nature_of_business, 1, 1, requested_loan_amount, guarantor_one, guarantor_one_contact, guarantor_one_relationship, guarantor_two, guarantor_two_contact, guarantor_two_relationship, "available", "available", purpose_for_loan)

            val response = ServiceBuilder.buildService(ApiInterfaceLoanApply::class.java)
            response.sendReq(applyRequestModel,"Bearer " + token).enqueue(
                object : Callback<ApplyResponseModel> {
                    override fun onResponse(
                        call: Call<ApplyResponseModel>,
                        response: Response<ApplyResponseModel>
                    ) {
                        Toast.makeText(this@LoanApplyActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                        println("we were successful")
                        println(response.message().toString())
                        println(response.body().toString())
                        println(response.body()?.id)
                        println(response.body()?.created_at)
                        val logintoken = response.body()?.created_at
                        val okResponse = response.message().toString()
                        if (okResponse == "Created"){
                            println("hello")
                            //start a new activity here
                            //start a new activity here

                            val intent = Intent(this@LoanApplyActivity, MyLoanApplicationsActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)

                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                        }
                    }

                    override fun onFailure(call: Call<ApplyResponseModel>, t: Throwable) {
                        Toast.makeText(this@LoanApplyActivity,t.toString(), Toast.LENGTH_LONG).show()
                        println("we failed")
                        println(t.toString())
                    }

                }
            )






        }




    }



}