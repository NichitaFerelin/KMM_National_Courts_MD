package com.ferelin.instantejustice.android.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent

fun Context.copyText(text: String, copyNotification: String) {
    val clip = ClipData.newPlainText("copy_message", text)
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, copyNotification, Toast.LENGTH_SHORT).show()
}

fun Context.openUrl(url: String) {
    val customTabs = CustomTabsIntent.Builder().build()

    customTabs.launchUrl(
        this,
        Uri.parse("https://docs.google.com/viewer?embedded=true&url=$url")
    )
}