package com.example.farmerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.farmerapp.ui.buyer.BuyerInterfaceScreen
import com.example.farmerapp.ui.farmer.AddProductScreen
import com.example.farmerapp.ui.farmer.FarmerDashboardScreen
import com.example.farmerapp.ui.farmer.ManageProductsScreen
import com.example.farmerapp.ui.farmer.EditProductScreen
import com.example.farmerapp.ui.screens.LoginScreen
import com.example.farmerapp.ui.screens.RegistrationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "farmer_dashboard") {
        // Login Screen
        composable(route = "login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToFarmerDashboard = { navController.navigate("farmer_dashboard") },
                onNavigateToBuyerDashboard = { navController.navigate("buyer_interface") } // Include buyer dashboard
            )
        }
        // Registration Screen
        composable("register") {
            RegistrationScreen(onNavigateToLogin = { navController.popBackStack() })
        }

        // Farmer Dashboard Screen
        composable(route = "farmer_dashboard") {
            FarmerDashboardScreen(
                onAddProduct = { navController.navigate(route = "add_product") },
                onManageProducts = { navController.navigate(route = "manage_products") },
                onLowStockNotifications = { /* Placeholder: Implement navigation or logic here */ }
            )
        }


        // Add Product Screen
        composable("add_product") {
            AddProductScreen(onBack = { navController.popBackStack() })
        }

        // Manage Products Screen
        composable("manage_products") {
            ManageProductsScreen(
                onBack = { navController.popBackStack() },
                onEditScreen = { id ->
                    navController.navigate(route = "edit?id=$id")
                }
            )
        }

        // Add Buyer Interface Screen
        composable("buyer_interface") {
            BuyerInterfaceScreen()
        }

        composable(
            route = "edit?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditProductScreen(onBack = { navController.popBackStack() })
        }

    }
}
