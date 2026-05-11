package com.hex.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hex.app.ui.screens.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen { navController.navigate("license") { popUpTo("splash") { inclusive = true } } } }
        composable("license") { LicenseScreen { navController.navigate("home") { popUpTo("license") { inclusive = true } } } }
        composable("home") { HomeScreen { route -> navController.navigate(route) } }
        composable("setup") { AttackSetupScreen { engine -> navController.currentBackStackEntry?.savedStateHandle?.set("engine", engine) ; navController.navigate("live") } }
        composable("live") {
            val engine = navController.previousBackStackEntry?.savedStateHandle?.get<com.hex.app.engine.AttackEngine>("engine")
            LiveAttackScreen(engine!!) { navController.navigate("results") { popUpTo("live") { inclusive = true } } }
        }
        composable("results") { ResultsScreen() }
        composable("settings") { SettingsScreen() }
    }
}
