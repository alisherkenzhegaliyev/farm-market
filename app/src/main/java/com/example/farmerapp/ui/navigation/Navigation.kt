package com.example.farmerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import com.example.farmerapp.ui.buyer.BuyerHomeScreen
import com.example.farmerapp.ui.buyer.BuyerInterfaceScreen
import com.example.farmerapp.ui.farmer.AddProductScreen
import com.example.farmerapp.ui.farmer.FarmerDashboardScreen
import com.example.farmerapp.ui.farmer.ManageProductsScreen
import com.example.farmerapp.ui.farmer.EditProductScreen
import com.example.farmerapp.ui.screens.LoginScreen
import com.example.farmerapp.ui.screens.RegistrationScreen

@Composable
fun AppNavigation(viewModel: NavigationViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)) {
    val navController = rememberNavController()

    val currentRoute = viewModel.currentRoute.collectAsState().value.name


    NavHost(navController = navController, startDestination = "farmer_dashboard") {
        // Login Screen
        composable(route = "login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToFarmerDashboard = { navController.navigate("farmer_dashboard") },
                onNavigateToBuyerDashboard = { navController.navigate("buyer_home") } // Include buyer dashboard
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
                navController = navController
            )
        }

        // Add Buyer Home Screen with Bottom Navigation
        composable("buyer_home") {
            BuyerHomeScreen() // Use your BuyerHomeScreen composable here
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
//                onDeleteProduct = { id ->
//                    // Add your delete logic here
//                    println("Delete product with id: $id")
//                }

            )
        }

        composable(
            route = "edit?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditProductScreen(onBack = { navController.popBackStack() })
        }

    }
}
