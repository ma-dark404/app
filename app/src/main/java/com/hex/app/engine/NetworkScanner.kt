package com.hex.app.engine

import kotlinx.coroutines.delay

suspend fun scanNetwork(): String? {
    delay(1500) // محاكاة
    return "http://192.168.1.1/login"
}
