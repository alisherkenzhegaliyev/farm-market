package com.example.farmerapp.ui.navigation

import CartScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import com.example.farmerapp.ui.General.ChatListScreen
import com.example.farmerapp.ui.General.ChatScreen
import com.example.farmerapp.ui.General.ProfileScreen
import com.example.farmerapp.ui.buyer.BuyerHomeScreen
import com.example.farmerapp.ui.buyer.OrderScreenUI
import com.example.farmerapp.ui.farmer.AddProductScreen
import com.example.farmerapp.ui.farmer.EditProductScreen
import com.example.farmerapp.ui.farmer.FarmerDashboardScreen
import com.example.farmerapp.ui.farmer.ManageProductsScreen
import com.example.farmerapp.ui.screens.LoginScreen
import com.example.farmerapp.ui.screens.RegistrationScreen

@Composable
fun AppNavigation(viewModel: NavigationViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)) {
    val navController = rememberNavController()
    val currentUserType = viewModel.currentUserType
    val currentRoute = viewModel.currentRoute.collectAsState().value.currentRoute.name


    NavHost(navController = navController, startDestination = "login") {
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
                onOrder = { navController.navigate(route = "order") },
                navController = navController
            )
        }

        // Add Buyer Home Screen with Bottom Navigation
        composable("buyer_home") {
            Log.i("BuyerHomeScreen", "BuyerHomeScreen")
            BuyerHomeScreen(navController) // Use your BuyerHomeScreen composable here
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

        composable(
            route = "edit?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditProductScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "chat_listing",
        ) {
            Log.i("HERE", "$currentUserType")
            val toNavigate = if (currentUserType == "Farmer") "farmer_dashboard" else "buyer_home"

            ChatListScreen(
                onChatSelected = { id -> navController.navigate("chat?id=$id") },
                onBack = { navController.navigate(toNavigate) },
            )
        }

        composable(
            route = "chat?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
            )
        ) {
            Log.i("ChatScreen", "ChatScreen from navigation with id: ${it.arguments?.getInt("id")}")
            ChatScreen(onBack = { navController.navigate("chat_listing") } )
        }

        composable(
            route = "profile"
        ) {
            val toNavigate = if (currentUserType == "Farmer") "farmer_dashboard" else "buyer_home"
            ProfileScreen(onBack = { navController.navigate(toNavigate) }, navigateToLogin = {
                navController.navigate("login")
            })
        }

        composable("cart") {
            val toNavigate = if (currentUserType == "Farmer") "farmer_dashboard" else "buyer_home"
            CartScreen(onBack = { navController.navigate(toNavigate) })
        }

        composable("order") {
            val toNavigate = if (currentUserType == "Farmer") "farmer_dashboard" else "buyer_home"
            OrderScreenUI(onBackClick = { navController.navigate(toNavigate) })
        }

    }
}
