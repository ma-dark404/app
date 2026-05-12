fun checkLicense(context: Context): LicenseStatus {
    return try {
        val lic = loadLicense(context)
        val hwid = getHWID(context)
        val now = System.currentTimeMillis()
        val trialDays = 3

        if (lic == null) {
            saveLicense(context, LicenseData(hwid = hwid))
            return LicenseStatus.Trial(trialDays)
        }

        // منع الكراش إذا كان highTime غير موجود
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
        if (now < trialEnd) LicenseStatus.Trial(((trialEnd - now) / 86_400_000).toInt())
        else LicenseStatus.TrialExpired
    } catch (e: Exception) {
        // في حالة أي خطأ، نعيد Trial بأمان
        LicenseStatus.Trial(3)
    }
}
