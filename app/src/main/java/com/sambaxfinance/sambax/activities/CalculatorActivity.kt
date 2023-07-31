package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar!!.setDisplayUseLogoEnabled(true)

        //let us initiate all the buttons
        val buttonGetPayBack = findViewById<Button>(R.id.buttonGetPayBack)
        val loan_inquiry_given = findViewById<EditText>(R.id.etLoanInquired)
        val loan_period_given = findViewById<EditText>(R.id.etLoanPeriodInquired)


        buttonGetPayBack.setOnClickListener {
            val loan_inquiry = loan_inquiry_given.text.toString().toIntOrNull() ?: 0
            val loan_period = loan_period_given.text.toString().toIntOrNull() ?: 0
            //val loan_inquiry2 = loan_inquiry_given2.text.toString().toIntOrNull() ?: 0
            //val loan_period2 = loan_period_given2.text.toString().toIntOrNull() ?: 0


            if(loan_inquiry == 0){
                loan_inquiry_given.error = "loan amount is required"
                loan_inquiry_given.requestFocus()
                return@setOnClickListener

            }
            if(loan_period == 0){
                loan_period_given.error = "loan amount is required"
                loan_period_given.requestFocus()
                return@setOnClickListener

            }

            val interest_rate = 0.00167
            val push_charge = 1000
            val communication_fee = 60 * loan_period
            val processing_fee = push_charge + communication_fee
            val interest = loan_inquiry * interest_rate * loan_period
            val loan_payable = loan_inquiry + interest + processing_fee
            val loan_payable_string = loan_payable.toString()
            val processing_fee_string = processing_fee.toString()

            //lets update textview with loan payable
            // Capture the layout's TextView and set the string as its text
            val textViewLoanResult = findViewById<TextView>(R.id.textViewLoanResult).apply {
                text = "Loan Payable: "+ loan_payable_string
            }
            //lets update textview of loan processing with the calculated fee
            // Capture the layout's TextView and set the string as its text
            val textViewLoanProcessingFee = findViewById<TextView>(R.id.textViewLoanProcessing).apply {
                text = "Loan Processing Fee: "+ processing_fee_string
            }

        }

    }
}