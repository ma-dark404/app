package com.hex.app.license

import android.content.Context
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    private fun getKey(context: Context): SecretKeySpec {
        val hwid = getHWID(context)
        return SecretKeySpec(hwid.toByteArray().copyOf(16), "AES")
    }

    fun encrypt(context: Context, data: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey(context))
        return Base64.encodeToString(cipher.doFinal(data.toByteArray()), Base64.DEFAULT)
    }

    fun decrypt(context: Context, encrypted: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, getKey(context))
        return String(cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT)))
    }
}
