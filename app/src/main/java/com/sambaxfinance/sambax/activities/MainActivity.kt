package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.sambaxfinance.sambax.R
import com.sambaxfinance.sambax.api.ApiInterfaceLogin
import com.sambaxfinance.sambax.api.ServiceBuilder
import com.sambaxfinance.sambax.models.LoginRequestModel
import com.sambaxfinance.sambax.models.LoginResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



const val EXTRA_MESSAGE = "com.example.sambax.MESSAGE"

class MainActivity : AppCompatActivity() {
    private val MY_REQUEST_CODE = 100



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo


        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.

                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE)
            }
        }


        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonCreateNewAccount = findViewById<Button>(R.id.buttonCreateNewAccount)

        val password_given = findViewById<EditText>(R.id.etPassword)
        val phone_number_given = findViewById<EditText>(R.id.etPhoneNumber)
        //val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        val tv_forgot_password = findViewById<TextView>(R.id.tv_forgot_password)

        buttonLogin.setOnClickListener {

            val password_fresh = password_given.text.toString().trim()
            //val phone_number_fish = phone_number.text.toString().trim()

            //val phone_number_fresh = phone_number_given.text.toString().toIntOrNull() ?: 0
            val phone_number_fresh = phone_number_given.text.toString().trim()



            /*
            if(phone_number_fresh == 0){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }*/

            if(phone_number_fresh.isEmpty()){
                phone_number_given.error = "phone number is required"
                phone_number_given.requestFocus()
                return@setOnClickListener

            }

            if(password_fresh.isEmpty()){
                password_given.error = "password is required"
                password_given.requestFocus()
                return@setOnClickListener

            }

            val phone_number_string = phone_number_fresh.toString()

            //DummyModel
            val requestModel = LoginRequestModel(phone_number_string, password_fresh)

            val response = ServiceBuilder.buildService(ApiInterfaceLogin::class.java)
            response.sendReq(phone_number_string, password_fresh).enqueue(
                object : Callback<LoginResponseModel> {
                    override fun onResponse(
                        call: Call<LoginResponseModel>,
                        response: Response<LoginResponseModel>
                    ) {
                        Toast.makeText(this@MainActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                        println("we were successful")
                        //println(response.message().toString())
                        //println(response.body().toString())
                        //println(response.body()?.access_token)
                        //println(response.body()?.token_type)
                        val token = response.body()?.access_token
                        val okResponse = response.message().toString()
                        if (okResponse == "OK"){
                            println("hello")
                            //clear error field
                            // Capture the layout's TextView and set the string as its text
                            val tvLoginResponse = findViewById<TextView>(R.id.tvLoginResponse).apply {
                                text = ""
                            }
                            //start a new activity here
                            val intent = Intent(this@MainActivity, NewLandingActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, token)
                            }
                            startActivity(intent)
                        }else{
                            println("no hello")
                            //show rejection in textview, refocus user to renter credentials
                            // Capture the layout's TextView and set the string as its text
                            //val wrongCredentialsMessage = "Wrong phone number or password"
                            // Capture the layout's TextView and set the string as its text
                            val tvLoginResponse = findViewById<TextView>(R.id.tvLoginResponse).apply {
                                text = "Wrong phone number or password"
                            }
                            password_given.requestFocus()
                            //return@setOnClickListener
                            Toast.makeText(this@MainActivity,"Wrong phone number or password",Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                        Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_LONG).show()
                        println("we failed")
                        //println(t.toString())
                        // Capture the layout's TextView and set the string as its text
                        val tvLoginResponse = findViewById<TextView>(R.id.tvLoginResponse).apply {
                            text = "please check your internet connection"
                        }
                    }

                }
            )

        }

        tvSignUp.setOnClickListener {
            println("you clicked signup")
            val intent = Intent(this, ChooseSignupActivity::class.java)
            startActivity(intent)
        }
        buttonCreateNewAccount.setOnClickListener {
            println("you clicked create new account")
            val intent = Intent(this, ChooseSignupActivity::class.java)
            startActivity(intent)
        }

        tv_forgot_password.setOnClickListener {
            println("you clicked forgot password")
            val intent = Intent(this, ChoosePasswordRecoverActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.

                val appUpdateManager = AppUpdateManagerFactory.create(this)
                // Returns an intent object that you use to check for an update.
                val appUpdateInfoTask = appUpdateManager.appUpdateInfo

                // Checks that the platform will allow the specified type of update.
                appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        // This example applies an immediate update. To apply a flexible update
                        // instead, pass in AppUpdateType.FLEXIBLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                    ) {
                        // Request the update.

                        appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE)
                    }
                }
            }
        }
    }

    // Checks that the update is not stalled during 'onResume()'.
    // However, you should execute this check at all entry points into the app.
    override fun onResume() {
        super.onResume()

        val appUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        MY_REQUEST_CODE
                    )
                }
            }
    }
}