package com.hex.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hex.app.license.LicenseManager
import com.hex.app.license.LicenseStatus
import com.hex.app.ui.theme.HexColors

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val context = LocalContext.current
    val status = remember { LicenseManager.checkLicense(context) }
    val statusText = when (status) {
        is LicenseStatus.Trial -> "Trial - ${status.days}d left"
        is LicenseStatus.Active -> "Active - ${status.days}d left"
        is LicenseStatus.Expired -> "Expired"
        is LicenseStatus.TrialExpired -> "Trial ended"
        is LicenseStatus.Tampered -> "Tampered!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HexColors.bgPrimary)
            .padding(16.dp)
    ) {
        Text("HEX Dashboard", style = MaterialTheme.typography.headlineSmall, color = HexColors.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(statusText, color = HexColors.accent, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onNavigate("setup") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HexColors.accent)
            ) { Text("New Attack", color = Color.Black) }

            Button(
                onClick = { onNavigate("settings") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HexColors.surface)
            ) { Text("Settings", color = HexColors.textPrimary) }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Saved Presets", color = HexColors.textSecondary, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(HexColors.surface),
            contentAlignment = Alignment.Center
        ) {
            Text("No presets yet", color = HexColors.textMuted)
        }
    }
}
