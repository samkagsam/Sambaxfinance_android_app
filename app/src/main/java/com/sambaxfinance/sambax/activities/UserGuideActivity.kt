package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R

class UserGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_guide)

        // Get the Intent that started this activity and extract the string
        val token = intent.getStringExtra(EXTRA_MESSAGE)

        // Call the setupToolbar function with the appropriate toolbar ID
        ToolbarUtils.setupToolbar(this, R.id.toolbar)

        //let us activate the bottom navigation menu
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewUserGuideActivity)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            NavigationBarUtils.handleNavigationBarItemClick(this, menuItem, token)
        }

        //let us initiatialize variables
        val webViewUserGuide = findViewById<WebView>(R.id.webViewUserGuide)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewUserGuide.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewUserGuide.loadUrl("https://www.sambaxfinance.com/user_guide_and_faqs")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewUserGuide.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewUserGuide.settings.setSupportZoom(true)
    }
    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewUserGuide = findViewById<WebView>(R.id.webViewUserGuide)
        // if your webview can go back it will go back
        if (webViewUserGuide.canGoBack())
            webViewUserGuide.goBack()
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