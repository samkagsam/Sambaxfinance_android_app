package com.sambaxfinance.sambax.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sambaxfinance.sambax.R

object NavigationUtils {
    fun setupBottomNavigation(activity: AppCompatActivity, bottomNavigationView: BottomNavigationView, menuResId: Int, token:String?) {
        bottomNavigationView.inflateMenu(menuResId)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_wallet -> {
                    // Handle click on Wallet menu item

                    //start a new activity here
                    val intent = Intent(activity, WalletActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    activity.startActivity(intent) // Use activity.startActivity()
                    true
                }
                R.id.menu_loans -> {
                    // Handle click on Wallet menu item

                    //start a new activity here
                    val intent = Intent(activity, LoansActivity::class.java).apply {
                        putExtra(EXTRA_MESSAGE, token)
                    }
                    activity.startActivity(intent) // Use activity.startActivity()
                    true
                }

                // Add more menu item cases as needed
                else -> false
            }
        }
    }
}
