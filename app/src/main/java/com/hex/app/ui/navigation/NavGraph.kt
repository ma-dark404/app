package com.hex.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hex.app.engine.AttackEngine
import com.hex.app.ui.screens.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen { navController.navigate("home") { popUpTo("splash") { inclusive = true } } }
        }

        composable("home") {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable("setup") {
            AttackSetupScreen { engine ->
                // تمرير المحرك عبر SavedStateHandle بأمان
                navController.currentBackStackEntry?.savedStateHandle?.set("engine", engine)
                navController.navigate("live")
            }
        }

        composable("live") {
            val engine = navController.previousBackStackEntry?.savedStateHandle?.get<AttackEngine>("engine")
            if (engine != null) {
                LiveAttackScreen(engine) { navController.navigate("results") { popUpTo("live") { inclusive = true } } }
            } else {
                // لو حصل خطأ رجوع للرئيسية
                HomeScreen(onNavigate = { navController.navigate("home") })
            }
        }

        composable("results") {
            ResultsScreen()
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}
