package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sambaxfinance.sambax.R

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)
        //let us initiatialize variables
        val webViewTerms = findViewById<WebView>(R.id.webViewTerms)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewTerms.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewTerms.loadUrl("https://www.sambaxfinance.com/terms_and_conditions")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewTerms.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewTerms.settings.setSupportZoom(true)
    }
    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewTerms = findViewById<WebView>(R.id.webViewTerms)
        // if your webview can go back it will go back
        if (webViewTerms.canGoBack())
            webViewTerms.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}