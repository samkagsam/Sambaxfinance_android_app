package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R

class UgxChooseAddMoneyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ugx_choose_add_money)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)


        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewUgxChooseAddMoneyActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }
        //let us initiate other variables
        val btn_go_to_use_mobile_money = findViewById<Button>(R.id.btn_go_to_use_mobile_money)
        val btn_go_to_use_card = findViewById<Button>(R.id.btn_go_to_use_card)



        btn_go_to_use_mobile_money.setOnClickListener {

            //start a new activity here
            val intent = Intent(this, UgxAddMoneyMobileActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
        }
        btn_go_to_use_card.setOnClickListener {
            //start a new activity here
            val intent = Intent(this, UgxAddMoneyByCardActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, token)
            }
            startActivity(intent)
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