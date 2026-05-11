package com.hex.app.license

import android.content.Context
import android.os.Build
import java.io.File

object TamperDetection {
    fun init(context: Context) {
        // Detect root (simplified)
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su"
        )
        for (path in paths) {
            if (File(path).exists()) {
                // Could log or take action
            }
        }
    }
}
