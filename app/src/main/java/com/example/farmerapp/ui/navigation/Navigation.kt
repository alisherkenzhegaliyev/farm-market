package com.example.farmerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmerapp.ui.screens.LoginScreen
import com.example.farmerapp.ui.screens.RegistrationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onNavigateToRegister = { navController.navigate("register") })
        }
        composable("register") {
            RegistrationScreen(onNavigateToLogin = { navController.navigate("login") })
        }
    }
}
