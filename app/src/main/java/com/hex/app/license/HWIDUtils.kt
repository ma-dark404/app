package com.hex.app.license

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest

fun getHWID(context: Context): String {
    val parts = mutableListOf<String>()
    parts.add("SERIAL:${Build.SERIAL}")
    parts.add("ANDROID_ID:${Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)}")
    parts.add("MODEL:${Build.MODEL}")
    parts.add("MANUFACTURER:${Build.MANUFACTURER}")
    parts.add("FINGERPRINT:${Build.FINGERPRINT}")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        parts.add("SERIAL_HW:${Build.getSerial()}")
    }
    return sha256(parts.joinToString("|")).take(32)
}

fun sha256(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
