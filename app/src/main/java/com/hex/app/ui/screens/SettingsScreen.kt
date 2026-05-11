package com.hex.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hex.app.license.LicenseManager
import com.hex.app.license.LicenseStatus
import com.hex.app.ui.theme.HexColors

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val status = remember { LicenseManager.checkLicense(context) }
    val statusText = when (status) {
        is LicenseStatus.Trial -> "Trial - ${status.days}d left"
        is LicenseStatus.Active -> "Active - ${status.days}d left"
        else -> "Expired"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HexColors.bgPrimary)
            .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall, color = HexColors.textPrimary)
        Spacer(modifier = Modifier.height(16.dp))

        // License card
        Card(
            colors = CardDefaults.cardColors(containerColor = HexColors.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("License Status: $statusText", color = HexColors.textSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { /* يدويًا */ }) { Text("Manage License") }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Telegram & GitHub أزرار بدون موارد خارجية
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Telegram زر
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/GoToHEX"))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088cc)),
                shape = MaterialTheme.shapes.medium
            ) {
                // أيقونة بسيطة بدلاً من drawable
                Text("📨", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Telegram", color = Color.White)
            }

            // GitHub زر
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ma-dark404"))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24292e)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("🐙", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("GitHub", color = Color.White)
            }
        }
    }
}
