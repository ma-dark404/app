package com.hex.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hex.app.engine.AttackEngine
import com.hex.app.engine.CredentialGenerator
import com.hex.app.engine.scanNetwork
import com.hex.app.ui.theme.HexColors

@Composable
fun AttackSetupScreen(onStart: (AttackEngine) -> Unit) {
    var targetUrl by remember { mutableStateOf("") }
    var scanning by remember { mutableStateOf(false) }
    var simulation by remember { mutableStateOf(true) }
    var userMode by remember { mutableStateOf("manual") }
    var passMode by remember { mutableStateOf("manual") }
    var manualUsers by remember { mutableStateOf(listOf("admin")) }
    var manualPasswords by remember { mutableStateOf(listOf("admin")) }
    var charset by remember { mutableStateOf("digits") }
    var length by remember { mutableStateOf(4) }
    var prefix by remember { mutableStateOf("") }
    var suffix by remember { mutableStateOf("") }
    var stopAfter by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(HexColors.bgPrimary).padding(16.dp)) {
        Text("Attack Setup", style = MaterialTheme.typography.headlineSmall, color = HexColors.textPrimary)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = targetUrl,
            onValueChange = { targetUrl = it },
            label = { Text("Target URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                scanning = true
                // scanNetwork will be launched in coroutine later
            }, enabled = !scanning) { Text(if (scanning) "Scanning..." else "Scan Network") }
            Spacer(Modifier.width(8.dp))
            Text("Simulation", color = HexColors.textSecondary)
            Switch(checked = simulation, onCheckedChange = { simulation = it })
        }

        Spacer(Modifier.height(16.dp))
        Text("Usernames", color = HexColors.accent, fontWeight = FontWeight.Bold)
        // Radio buttons for mode
        Row {
            RadioButton(selected = userMode == "manual", onClick = { userMode = "manual" })
            Text("Manual")
            RadioButton(selected = userMode == "generated", onClick = { userMode = "generated" })
            Text("Generated")
        }
        if (userMode == "manual") {
            manualUsers.forEachIndexed { index, user ->
                OutlinedTextField(value = user, onValueChange = { newList -> manualUsers = manualUsers.toMutableList().also { it[index] = newList } }, label = { Text("User ${index+1}") })
            }
            Button(onClick = { manualUsers = manualUsers + "" }) { Text("Add User") }
        } else {
            OutlinedTextField(value = charset, onValueChange = { charset = it }, label = { Text("Charset") })
            OutlinedTextField(value = length.toString(), onValueChange = { length = it.toIntOrNull() ?: 4 }, label = { Text("Length") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            OutlinedTextField(value = prefix, onValueChange = { prefix = it }, label = { Text("Prefix") })
            OutlinedTextField(value = suffix, onValueChange = { suffix = it }, label = { Text("Suffix") })
        }

        Spacer(Modifier.height(16.dp))
        Text("Passwords", color = HexColors.accent, fontWeight = FontWeight.Bold)
        Row {
            RadioButton(selected = passMode == "manual", onClick = { passMode = "manual" })
            Text("Manual")
            RadioButton(selected = passMode == "generated", onClick = { passMode = "generated" })
            Text("Generated")
        }
        // similar to usernames (omitted for brevity, but you can extend)

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = stopAfter.toString(), onValueChange = { stopAfter = it.toIntOrNull() ?: 0 }, label = { Text("Stop after N (0=all)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                val users = if (userMode == "manual") manualUsers else CredentialGenerator.generate("generated", charset, length, prefix, suffix, listOf())
                val passwords = if (passMode == "manual") manualPasswords else users // demo
                val engine = AttackEngine(users, passwords, stopAfter, targetUrl.ifEmpty { "http://192.168.1.1/login" }, simulation)
                onStart(engine)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HexColors.accent)
        ) { Text("Start Attack", color = androidx.compose.ui.graphics.Color.Black) }
    }
}
