package com.hex.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // تم إصلاح الخطأ 1: استيراد sp
import com.hex.app.license.LicenseManager
import com.hex.app.license.LicenseStatus
import com.hex.app.license.getHWID
import com.hex.app.ui.theme.HexColors
import kotlinx.coroutines.CoroutineScope // تم إصلاح الخطأ 2: استيراد الكوروتين
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LicenseScreen(onActivated: () -> Unit) {
    val context = LocalContext.current
    var key by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // تم إصلاح الخطأ 2: استخدام rememberCoroutineScope

    val status = remember { LicenseManager.checkLicense(context) }
    val statusText = when (status) {
        is LicenseStatus.Trial -> "Trial - ${status.days} days left"
        is LicenseStatus.Active -> "Active - ${status.days} days left"
        else -> "Expired / Tampered"
    }

    Box(modifier = Modifier.fillMaxSize().background(HexColors.bgPrimary), contentAlignment = Alignment.Center) {
        Card(colors = CardDefaults.cardColors(containerColor = HexColors.surface), modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Activation Required", color = HexColors.accent, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("HWID: ${getHWID(context).take(8)}...", color = HexColors.textSecondary, fontSize = 12.sp)
                Spacer(Modifier.height(4.dp))
                Text(statusText, color = HexColors.textMuted, fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = key,
                    onValueChange = { key = it },
                    label = { Text("License Key") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        loading = true
                        // تم إصلاح الخطأ 2: تشغيل delay داخل كوروتين
                        scope.launch {
                            if (LicenseManager.activateLicense(context, key)) {
                                message = "Activated!"
                                delay(500)
                                onActivated()
                            } else {
                                message = "Invalid key"
                            }
                            loading = false
                        }
                    },
                    enabled = !loading
                ) {
                    Text(if (loading) "Activating..." else "Activate")
                }
                if (message.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(message, color = HexColors.textSecondary, fontSize = 13.sp)
                }
            }
        }
    }
}
