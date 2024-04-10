package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.*
import com.sambaxfinance.sambax.models.FixedAccountLandingResponseModel
import com.sambaxfinance.sambax.models.GetTotalRequestsResponseModel
import com.sambaxfinance.sambax.models.LandingPageResponseModel
import com.sambaxfinance.sambax.models.WalletResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewLandingActivity : AppCompatActivity() {

    private lateinit var tvAccountBalance: TextView
    private lateinit var forwardIcon: ImageView
    private lateinit var backIcon: ImageView

    // List to store all the balance values
    private lateinit var balanceValues: List<String>
    // Index to keep track of the current balance being displayed
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_landing)

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
        val button_go_to_wallet = findViewById<Button>(R.id.button_go_to_wallet)
        val button_go_to_send_money = findViewById<Button>(R.id.button_go_to_send_money)
        val button_go_to_exchange_currency = findViewById<Button>(R.id.button_go_to_exchange_currency)
        val button_go_to_loans = findViewById<Button>(R.id.button_go_to_loans)
        val buttonGoToGroupSavings = findViewById<Button>(R.id.buttonGoToGroupSavings)

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // Find views
        tvAccountBalance = findViewById(R.id.tvAccountBalance)
        forwardIcon = findViewById(R.id.forwardIcon)
        backIcon = findViewById(R.id.backIcon)

        // Set click listeners
        forwardIcon.setOnClickListener { onForwardIconClick() }
        backIcon.setOnClickListener { onBackIconClick() }

        // Load initial data
        loadData()




        // Change gravity of the toolbar to move hamburger icon to the right
        val params = toolbar.layoutParams as LinearLayout.LayoutParams
        params.gravity = Gravity.END // Set gravity to end (right)
        toolbar.layoutParams = params

        //val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        // Hide the overflow menu icon
        toolbar.overflowIcon = null

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)


        val response = ServiceBuilder.buildService(ApiInterfaceWallet::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<WalletResponseModel> {
                override fun onResponse(
                    call: Call<WalletResponseModel>,
                    response: Response<WalletResponseModel>
                ) {
                    Toast.makeText(this@NewLandingActivity,response.message().toString(), Toast.LENGTH_LONG).show()
                    //println("we were successful")
                    //println(response.message().toString())
                    //println(response.body().toString())
                    //println(response.body()?.first_name)
                    //println(response.body()?.account_balance)
                    //println(response.body()?.loan_balance)
                    //val logintoken = response.body()?.access_token
                    val okResponse = response.message().toString()

                    val ugx_balance = response.body()?.ugx_balance
                    val usd_balance = response.body()?.usd_balance
                    val sar_balance = response.body()?.sar_balance
                    val qar_balance = response.body()?.qar_balance
                    val aed_balance = response.body()?.aed_balance

                    // Capture the layout's TextView and set the string as its text
                    val tv_hello = findViewById<TextView>(R.id.tvHello).apply {
                        text = "Hello "+ response.body()?.name
                    }

                    // Capture the layout's TextView and set the string as its text
                    val tv_account_balance = findViewById<TextView>(R.id.tvAccountBalance).apply {
                        text =  "UGX " + ugx_balance
                    }

                }

                override fun onFailure(call: Call<WalletResponseModel>, t: Throwable) {
                    Toast.makeText(this@NewLandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    //println("we failed")
                    //println(t.toString())
                }

            }
        )



        getTotalWeeklyGroupRequests(token.toString())
        getTotalLongTermGroupRequests(token.toString())


        button_go_to_wallet.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@NewLandingActivity, WalletActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_go_to_send_money.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@NewLandingActivity, SendMoneyActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        button_go_to_exchange_currency.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@NewLandingActivity, CurrencyExchangeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

        button_go_to_loans.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@NewLandingActivity, LoansActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }

        buttonGoToGroupSavings.setOnClickListener {
            //start a new activity here
            val intent = Intent(this@NewLandingActivity, GroupTypeActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }





        navigationView.setNavigationItemSelectedListener { item ->
            val id = item.itemId

            // Handle menu item clicks here
            when (item.itemId) {
                R.id.action_one -> {
                    // Handle action one click
                    println("you clicked about us")
                    // For example, navigate to a specific fragment or perform an action
                    val intent = Intent(this@NewLandingActivity, AboutActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.action_two -> {
                    // Handle action two click
                    println("you clicked terms")
                    val intent = Intent(this@NewLandingActivity, TermsActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.action_three -> {
                    //Toast.makeText(this, "Item Three Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@NewLandingActivity, UserGuideActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.action_four -> {
                    //Toast.makeText(this, "Item four Clicked", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@NewLandingActivity, ContactUsActivity::class.java)
                    startActivity(intent)

                    true
                }
                R.id.action_five -> {
                    //start a new activity here
                    val intent = Intent(this@NewLandingActivity, MyLoanApplicationsActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    startActivity(intent)

                    true
                }
                R.id.action_six -> {
                    //start a new activity here
                    val intent = Intent(this@NewLandingActivity, CalculatorActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    startActivity(intent)

                    true
                }
                R.id.action_seven -> {
                    //start a new activity here
                    val intent = Intent(this@NewLandingActivity, BlogActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    startActivity(intent)

                    true
                }
                R.id.action_nine -> {
                    //start a new activity here
                    val intent = Intent(this@NewLandingActivity, DeleteAccountActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    startActivity(intent)

                    true
                }
                R.id.action_ten -> {
                    //start a new activity here
                    val intent = Intent(this@NewLandingActivity, MainActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    startActivity(intent)

                    true
                }
                // Add more cases for other menu items if needed

                else -> false
            }

            drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun loadData() {
        // Perform API request to get wallet data
        val token = intent.getStringExtra(EXTRA_MESSAGE)
        val response = ServiceBuilder.buildService(ApiInterfaceWallet::class.java)
        response.sendReq("Bearer $token").enqueue(object : Callback<WalletResponseModel> {
            override fun onResponse(
                call: Call<WalletResponseModel>,
                response: Response<WalletResponseModel>
            ) {
                if (response.isSuccessful) {
                    val walletData = response.body()
                    walletData?.let {
                        // Extract all balance values from the response
                        balanceValues = listOf(
                            "UGX " + it.ugx_balance,
                            "USD " + it.usd_balance,
                            "SAR " + it.sar_balance,
                            "QAR " + it.qar_balance,
                            "AED " + it.aed_balance
                        )
                        // Update the UI with the initial balance value
                        updateBalance(balanceValues[currentIndex])
                    }
                } else {
                    Toast.makeText(
                        this@NewLandingActivity,
                        "Failed to load data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<WalletResponseModel>, t: Throwable) {
                Toast.makeText(
                    this@NewLandingActivity,
                    "Failed to load data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateBalance(balance: String) {
        tvAccountBalance.text = balance
    }

    private fun onForwardIconClick() {
        // Increment the index to move to the next balance value
        currentIndex = (currentIndex + 1) % balanceValues.size
        // Update UI with the new balance value
        updateBalance(balanceValues[currentIndex])
    }

    private fun onBackIconClick() {
        // Decrement the index to move to the previous balance value
        currentIndex = (currentIndex - 1 + balanceValues.size) % balanceValues.size
        // Update UI with the new balance value
        updateBalance(balanceValues[currentIndex])
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_drawer, menu)
        return true
    }
    fun getTotalWeeklyGroupRequests(token:String){

        val response = ServiceBuilder.buildService(ApiInterfaceGetTotalGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<GetTotalRequestsResponseModel> {
                override fun onResponse(
                    call: Call<GetTotalRequestsResponseModel>,
                    response: Response<GetTotalRequestsResponseModel>
                ) {

                    val okResponse = response.message().toString()

                    val request_total = response.body()?.total_requests
                    //println(WeeklyGroupRequests.request_total)

                    //return request_total

                    if(request_total != 0){
                        // Capture the layout's TextView and set the string as its text
                        val tv_group_requests = findViewById<TextView>(R.id.tvShowWeeklyGroupNotifications).apply {
                            text = request_total.toString()
                        }
                    }






                }

                override fun onFailure(call: Call<GetTotalRequestsResponseModel>, t: Throwable) {
                    //Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )


        //println(request_total)
        println("eya")

        //return request_total!!.toInt()
        //return 3

    }

    fun getTotalLongTermGroupRequests(token:String){

        val response = ServiceBuilder.buildService(ApiInterfaceGetTotalLongTermGroupRequests::class.java)
        response.sendReq("Bearer " + token).enqueue(
            object : Callback<GetTotalRequestsResponseModel> {
                override fun onResponse(
                    call: Call<GetTotalRequestsResponseModel>,
                    response: Response<GetTotalRequestsResponseModel>
                ) {

                    val okResponse = response.message().toString()
                    val request_total = response.body()?.total_requests
                    println(request_total)

                    if(request_total != 0){
                        // Capture the layout's TextView and set the string as its text
                        val tv_group_requests = findViewById<TextView>(R.id.tvShowLongTermGroupNotifications).apply {
                            text = request_total.toString()
                        }
                    }




                }

                override fun onFailure(call: Call<GetTotalRequestsResponseModel>, t: Throwable) {
                    //Toast.makeText(this@LandingActivity,t.toString(), Toast.LENGTH_LONG).show()
                    println("we failed")
                    //println(t.toString())
                }

            }
        )


        //println(request_total)
        println("yalelo")


        //return request_total!!.toInt()
        //return 2

    }
}