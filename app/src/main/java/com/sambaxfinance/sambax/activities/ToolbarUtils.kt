package com.sambaxfinance.sambax.activities
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sambaxfinance.sambax.R


object ToolbarUtils {
    fun setupToolbar(activity: Activity, toolbarId: Int) {
        val toolbar = activity.findViewById<Toolbar>(toolbarId)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        activity.supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.sambax_logo_1_24)
            setDisplayUseLogoEnabled(true)
        }
    }
}