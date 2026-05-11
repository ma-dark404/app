package com.hex.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hex.app.ui.theme.HexColors

@Composable
fun ResultsScreen() {
    // In a real app, fetch from ViewModel or file
    Column(modifier = Modifier.fillMaxSize().background(HexColors.bgPrimary).padding(16.dp)) {
        Text("Results", style = MaterialTheme.typography.headlineSmall, color = HexColors.textPrimary)
        Spacer(Modifier.height(16.dp))
        Text("No recent discoveries", color = HexColors.textMuted)
    }
}
