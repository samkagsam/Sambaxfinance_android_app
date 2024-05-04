package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R

class HowGroupsWorkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_groups_work)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewHowGroupsWorkActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiatialize variables
        val webViewAbout = findViewById<WebView>(R.id.webViewHowGroupsWork)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewAbout.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewAbout.loadUrl("https://www.sambaxfinance.com/how_groups_work")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewAbout.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewAbout.settings.setSupportZoom(true)
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewAbout = findViewById<WebView>(R.id.webViewHowGroupsWork)
        // if your webview can go back it will go back
        if (webViewAbout.canGoBack())
            webViewAbout.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
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