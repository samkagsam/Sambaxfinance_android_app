package com.sambaxfinance.sambax.activities

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.sambaxfinance.sambax.R

object NavigationBarUtils {
    fun handleNavigationBarItemClick(activity: Activity, item: MenuItem, token: String?): Boolean {
        return when (item.itemId) {
            R.id.menu_wallet -> {
                val intent = Intent(activity, WalletActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
                true
            }
            R.id.menu_loans -> {
                val intent = Intent(activity, LoansActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
                true
            }
            R.id.menu_send -> {
                val intent = Intent(activity, SendMoneyActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
                true
            }

            else -> false
        }
    }
}