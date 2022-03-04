package com.vm.office.common_ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.annotation.StringRes

/**
 * Extension of Context.
 *
 * Utility method that configures a target View to start a `send e-mail` intent.
 *
 * @param target view for which the email sender would be registered
 * @param emailId EXTRA_EMAIL value to be passed in the Intent
 * @param appChooserTitle If more than one E-Mail app is configured. The chooser dialog will
 * display this title.
 */
fun Context.registerViewForEmailSender(
    target: View?,
    emailId: String,
    @StringRes appChooserTitle: Int
) {
    target?.setOnClickListener {
        val sendIntent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            data = Uri.parse("mailto:");
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailId))
        }

        startActivity(
            Intent.createChooser(
                sendIntent,
                getString(appChooserTitle)
            )
        )
    }
}