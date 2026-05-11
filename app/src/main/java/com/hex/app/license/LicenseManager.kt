package com.hex.app.license

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.File

data class LicenseData(
    val hwid: String,
    @SerializedName("first_run") val firstRun: Long = System.currentTimeMillis(),
    @SerializedName("last_run") var lastRun: Long = System.currentTimeMillis(),
    @SerializedName("high_time") var highTime: Long = System.currentTimeMillis(),
    var activated: Boolean = false,
    var expiry: Long = 0L
)

sealed class LicenseStatus {
    data class Trial(val days: Int) : LicenseStatus()
    data class Active(val days: Int) : LicenseStatus()
    object Expired : LicenseStatus()
    object TrialExpired : LicenseStatus()
    object Tampered : LicenseStatus()
}

object LicenseManager {
    private const val FILE_NAME = ".hex_license"
    private val gson = Gson()

    fun loadLicense(context: Context): LicenseData? {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return null
        return try {
            val encrypted = file.readText()
            val json = CryptoUtils.decrypt(context, encrypted)
            gson.fromJson(json, LicenseData::class.java)
        } catch (e: Exception) { null }
    }

    fun saveLicense(context: Context, data: LicenseData) {
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(CryptoUtils.encrypt(context, gson.toJson(data)))
    }

    fun checkLicense(context: Context): LicenseStatus {
        val lic = loadLicense(context)
        val hwid = getHWID(context)
        val now = System.currentTimeMillis()
        val trialDays = 3

        if (lic == null) {
            saveLicense(context, LicenseData(hwid = hwid))
            return LicenseStatus.Trial(trialDays)
        }

        if (now < lic.highTime - 300_000) {
            lic.activated = false
            saveLicense(context, lic)
            return LicenseStatus.Tampered
        }
        if (now > lic.highTime) lic.highTime = now
        lic.lastRun = now
        saveLicense(context, lic)

        if (lic.activated) {
            return if (now < lic.expiry) LicenseStatus.Active(((lic.expiry - now) / 86_400_000).toInt())
            else LicenseStatus.Expired
        }

        val trialEnd = lic.firstRun + trialDays * 86_400_000L
        return if (now < trialEnd) LicenseStatus.Trial(((trialEnd - now) / 86_400_000).toInt())
        else LicenseStatus.TrialExpired
    }

    fun activateLicense(context: Context, key: String): Boolean {
        if (key.length < 10) return false
        val lic = loadLicense(context) ?: LicenseData(hwid = getHWID(context))
        lic.activated = true
        lic.expiry = System.currentTimeMillis() + 365 * 86_400_000L
        saveLicense(context, lic)
        return true
    }
}
