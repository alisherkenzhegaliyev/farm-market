//package com.example.farmerapp.ui.buyer
//
//import CartScreen
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.Chat
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.farmerapp.model.Chat
//import com.example.farmerapp.ui.General.ChatListScreen
//import com.example.farmerapp.ui.General.ChatScreen
//import com.example.farmerapp.ui.General.ProfileScreen
//
//@Composable
//fun BuyersHomeScreen(
//    navController: NavHostController
//) {
//    // Use rememberNavController to get a NavHostController
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { innerPadding ->
//        // Pass the navController to NavigationHost
//        NavigationHost(
//            navController = navController,
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
//
//@Composable
//fun BottomNavigationBar(
//    navController: NavController,
//) {
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = currentBackStackEntry?.destination?.route
//
//    NavigationBar(
//        containerColor = Color.White, // Background color of the navigation bar
//        tonalElevation = 4.dp // Elevation effect
//    ) {
//        val navItems = listOf(
//            NavigationItem("Home", Icons.Default.Home, "home"),
//            NavigationItem("Chats", Icons.AutoMirrored.Filled.Chat, "chat_listing"),
//            NavigationItem("Cart", Icons.Default.ShoppingCart, "cart"),
//            NavigationItem("Profile", Icons.Default.Person, "profile")
//        )
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.label,
//                        tint = if (currentRoute == item.route) Color(0xFF388E3C) else Color.LightGray // Explicitly set tint
//                    )
//                },
//                label = {
//                    Text(
//                        text = item.label,
//                        color = if (currentRoute == item.route) Color(0xFF388E3C) else Color.LightGray // Explicitly set text color
//                    )
//                },
//                selected = currentRoute == item.route,
//                onClick = {
//                    if (currentRoute != item.route) {
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationId) { saveState = true }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color(0xFF388E3C), // Fallback for selected icon
//                    selectedTextColor = Color(0xFF388E3C), // Fallback for selected text
//                    unselectedIconColor = Color.Gray,
//                    unselectedTextColor = Color.Gray
//                ),
//                alwaysShowLabel = true
//            )
//        }
//    }
//}
//
//@Composable
//fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
//    // Use navController directly, as it's now the correct type
//    NavHost(
//        navController = navController,
//        startDestination = "home",
//        modifier = modifier
//    ) {
//        composable("home") { BuyerDashboardScreen() }
//        composable("chat_listing") { ChatListScreen(navController.navigate("chat") ) }
//        composable("profile") { ProfileScreen() }
//        composable("cart") { CartScreen() }
//    }
//}
//
//
//@Composable
//fun BuyerDashboardScreen() {
//    // Use your existing Buyer Dashboard UI here
//    BuyerInterfaceScreen()
//}
//
//
