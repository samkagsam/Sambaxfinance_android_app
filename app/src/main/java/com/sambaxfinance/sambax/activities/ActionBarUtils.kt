package com.sambaxfinance.sambax.activities
import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.sambaxfinance.sambax.R

object ActionBarUtils {
    fun handleActionBarItemClick(activity: Activity, item: MenuItem, token: String?) {
        val id = item.itemId
        when (id) {
            R.id.action_about -> {
                val intent = Intent(activity, AboutActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_terms -> {
                val intent = Intent(activity, TermsActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_user_guide -> {
                val intent = Intent(activity, UserGuideActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_contact_us -> {
                val intent = Intent(activity, ContactUsActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_fix_funds -> {
                val intent = Intent(activity, FixedAccountLandingActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_financial_literacy -> {
                val intent = Intent(activity, BlogActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_delete_account -> {
                val intent = Intent(activity, DeleteAccountActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }
            R.id.action_log_out -> {
                val intent = Intent(activity, MainActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, token)
                }
                activity.startActivity(intent)
            }

            // Add more cases for other menu items if needed
        }
    }
}