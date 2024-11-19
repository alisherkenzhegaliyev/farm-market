package com.example.farmerapp.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FarmerDashboardScreen(
    onAddProduct: () -> Unit,
    onManageProducts: () -> Unit,
    onLowStockNotifications: () -> Unit
) {
    Scaffold(
        topBar = { FarmerTopBar() },
        content = { padding ->
            FarmerDashboardContent(
                modifier = Modifier.padding(padding),
                onAddProduct = onAddProduct,
                onManageProducts = onManageProducts,
                onLowStockNotifications = onLowStockNotifications
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerTopBar() {
    TopAppBar(
        title = { Text("Farmer Dashboard", fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun FarmerDashboardContent(
    modifier: Modifier = Modifier,
    onAddProduct: () -> Unit,
    onManageProducts: () -> Unit,
    onLowStockNotifications: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onAddProduct,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Add Product")
        }

        Button(
            onClick = onManageProducts,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("View/Edit Products")
        }

        Button(
            onClick = onLowStockNotifications,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Low-Stock Notifications")
        }
    }
}
