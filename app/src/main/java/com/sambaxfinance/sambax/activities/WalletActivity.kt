package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceWallet
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.WalletResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        // assigning ID of the toolbar to a variable
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        // Display application icon in the toolbar
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.sambax_logo_1_24)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Adding hamburger icon

        //let us initiate all the buttons
        val transaction_history = findViewById<TextView>(R.id.transaction_history)
        val exchange_currency_view = findViewById<TextView>(R.id.exchange_currency_view)
        val btn_ugx_add_money = findViewById<Button>(R.id.btn_ugx_add_money)
        val btn_ugx_withdraw = findViewById<Button>(R.id.btn_ugx_withdraw)
        val btn_ugx_fix = findViewById<Button>(R.id.btn_ugx_fix)
        val btn_usd_add_money = findViewById<Button>(R.id.btn_usd_add_money)
        val btn_sar_add_money = findViewById<Button>(R.id.btn_sar_add_money)
        val btn_qar_add_money = findViewById<Button>(R.id.btn_qar_add_money)
        val btn_aed_add_money = findViewById<Button>(R.id.btn_aed_add_money)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)


        val response = ServiceBuilder.buildService(ApiInterfaceWallet::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<WalletResponseModel> {
                override fun onResponse(
                    call: Call<WalletResponseModel>,
                    response: Response<WalletResponseModel>
                ) {
                    Toast.makeText(this@WalletActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()

                    //val ugx_balance = response.body()?.ugx_balance
                    //val usd_balance = response.body()?.usd_balance
                    //val sar_balance = response.body()?.sar_balance
                    //val qar_balance = response.body()?.qar_balance
                    //val aed_balance = response.body()?.aed_balance

                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.ugx_balance).apply {
                        text = "UGX "+ response.body()?.ugx_balance
                    }

                    // Capture the layout's TextView and set the string as its text
                    val usd_balance = findViewById<TextView>(R.id.usd_balance).apply {
                        text =  "USD " + response.body()?.usd_balance
                    }
                    // Capture the layout's TextView and set the string as its text
                    val sar_balance = findViewById<TextView>(R.id.sar_balance).apply {
                        text =  "SAR " + response.body()?.sar_balance
                    }
                    // Capture the layout's TextView and set the string as its text
                    val qar_balance = findViewById<TextView>(R.id.qar_balance).apply {
                        text =  "QAR " + response.body()?.qar_balance
                    }
                    // Capture the layout's TextView and set the string as its text
                    val aed_balance = findViewById<TextView>(R.id.aed_balance).apply {
                        text =  "AED " + response.body()?.aed_balance
                    }

                }

                override fun onFailure(call: Call<WalletResponseModel>, t: Throwable) {
                    Toast.makeText(this@WalletActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )
        transaction_history.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, StatementActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        exchange_currency_view.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, CurrencyExchangeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_ugx_add_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, UgxChooseAddMoneyActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_ugx_withdraw.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, UgxWithdrawByMobileMoneyActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_ugx_fix.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, FixedAccountLandingActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_usd_add_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, UsdAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_sar_add_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, SarAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_qar_add_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, QarAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_aed_add_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@WalletActivity, AedAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }


    }
}