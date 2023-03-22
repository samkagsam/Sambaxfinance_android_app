package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sambaxfinance.sambax.R

class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        //let us initiatialize variables
        val webViewContactUs = findViewById<WebView>(R.id.webViewContactUs)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewContactUs.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewContactUs.loadUrl("https://www.sambaxfinance.com/contact_us")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewContactUs.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewContactUs.settings.setSupportZoom(true)
    }
    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewContactUs = findViewById<WebView>(R.id.webViewContactUs)
        // if your webview can go back it will go back
        if (webViewContactUs.canGoBack())
            webViewContactUs.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}