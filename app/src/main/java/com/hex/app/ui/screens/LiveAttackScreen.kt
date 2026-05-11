package com.hex.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hex.app.engine.AttackEngine
import com.hex.app.ui.theme.HexColors
import kotlinx.coroutines.launch

@Composable
fun LiveAttackScreen(engine: AttackEngine, onStop: () -> Unit) {
    val log by engine.log.collectAsState()
    val progress by engine.progress.collectAsState()
    val discoveries by engine.discoveries.collectAsState()
    val running by engine.running.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        engine.start(scope)
    }

    Column(modifier = Modifier.fillMaxSize().background(HexColors.bgPrimary).padding(16.dp)) {
        Text("ATTACK IN PROGRESS", color = HexColors.accent, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Progress: ${progress.first}/${progress.second}", color = HexColors.textSecondary)
            Text("Found: $discoveries", color = HexColors.success)
        }
        Spacer(Modifier.height(12.dp))

        // Terminal log
        Box(modifier = Modifier.weight(1f).background(Color.Black, shape = MaterialTheme.shapes.small)) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(log) { attempt ->
                    Text(
                        text = "${if (attempt.status == "success") "[+]" else "[*]"} ${attempt.username}:${attempt.password}",
                        color = if (attempt.status == "success") HexColors.success else HexColors.textMuted,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {
                engine.stop()
                onStop()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = HexColors.danger)
        ) { Text("STOP ATTACK") }
    }
}
