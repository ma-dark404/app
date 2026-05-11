package com.hex.app.engine

object CredentialGenerator {
    fun generate(
        mode: String,
        charset: String,
        length: Int,
        prefix: String,
        suffix: String,
        manualList: List<String>
    ): List<String> {
        if (mode == "manual") return manualList.filter { it.isNotBlank() }
        val chars = when (charset) {
            "digits" -> "0123456789"
            "letters" -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            else -> "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        }
        val free = length - prefix.length - suffix.length
        if (free <= 0) return listOf(prefix + suffix)
        val result = mutableListOf<String>()
        fun recurse(current: String, remaining: Int) {
            if (remaining == 0) {
                result.add(prefix + current + suffix)
                return
            }
            for (c in chars) {
                recurse(current + c, remaining - 1)
                if (result.size >= 20000) break
            }
        }
        recurse("", free)
        return result.take(20000)
    }
}
