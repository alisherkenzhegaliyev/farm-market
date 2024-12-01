package com.example.farmerapp.ui.farmer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import com.example.farmerapp.ui.buyer.NavigationItem

@Composable
fun FarmerDashboardScreen(
    onAddProduct: () -> Unit,
    onManageProducts: () -> Unit,
    navController: NavController // Pass NavController to FarmerDashboardScreen
) {
    Scaffold(
        topBar = { FarmerTopBar() },
        content = { padding ->
            FarmerDashboardContent(
                modifier = Modifier.padding(padding),
                onAddProduct = onAddProduct,
                onManageProducts = onManageProducts,
            )
        },
        bottomBar = { FarmerBottomBar(navController = navController) } // Add BottomNavigationBar here
    )
}

@Composable
fun FarmerBottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White, // Background color of the navigation bar
        tonalElevation = 4.dp // Elevation effect
    ) {
        val navItems = listOf(
            NavigationItem("Home", Icons.Default.Home, "home"),
            NavigationItem("Chat", Icons.Filled.Chat, "chat"),
            NavigationItem("Profile", Icons.Default.Person, "profile")
        )

        navItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) Color(0xFF398E3D) else Color.LightGray // Explicitly set tint
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (currentRoute == item.route) Color(0xFF388E3C) else Color.LightGray // Explicitly set text color
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF388E3C), // Fallback for selected icon
                    selectedTextColor = Color(0xFF388E3C), // Fallback for selected text
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
                alwaysShowLabel = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerTopBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Agriculture, contentDescription = "Farmer Icon", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Farmer Dashboard", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}



@Composable
fun FarmerDashboardContent(
    modifier: Modifier = Modifier,
    onAddProduct: () -> Unit,
    onManageProducts: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Dashboard Cards
            DashboardCard(
                text = "Add Product",
                icon = Icons.Default.Add,
                backgroundColor = Color(0xFF66BB6A),
                onClick = onAddProduct
            )

            DashboardCard(
                text = "View/Edit Products",
                icon = Icons.Default.Edit,
                backgroundColor = Color(0xFF4CAF50),
                onClick = onManageProducts
            )
        }
    }
}

@Composable
fun DashboardCard(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$text Icon",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
