package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sambaxfinance.sambax.R

class HowFixedDepositWorksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_fixed_deposit_works)

        //let us initiatialize variables
        val webViewAbout = findViewById<WebView>(R.id.webViewHowFixedDepositWorks)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewAbout.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewAbout.loadUrl("https://sambaxfinance.com/fixed_deposit_account")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewAbout.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewAbout.settings.setSupportZoom(true)
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewAbout = findViewById<WebView>(R.id.webViewHowFixedDepositWorks)
        // if your webview can go back it will go back
        if (webViewAbout.canGoBack())
            webViewAbout.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}