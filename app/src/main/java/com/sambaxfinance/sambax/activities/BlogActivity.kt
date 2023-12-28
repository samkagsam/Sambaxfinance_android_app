package com.sambaxfinance.sambax.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sambaxfinance.sambax.R

class BlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        //let us initiatialize variables
        val webViewBlog = findViewById<WebView>(R.id.webViewBlog)

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webViewBlog.webViewClient = WebViewClient()

        // this will load the url of the website
        webViewBlog.loadUrl("https://www.sambaxfinance.com/blog/")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webViewBlog.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webViewBlog.settings.setSupportZoom(true)
    }
    // if you press Back button this code will work
    override fun onBackPressed() {
        val webViewBlog = findViewById<WebView>(R.id.webViewBlog)
        // if your webview can go back it will go back
        if (webViewBlog.canGoBack())
            webViewBlog.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}