package com.example.farmerapp.ui.farmer
import androidx.compose.material.icons.outlined.Agriculture

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    onLowStockNotifications: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C)) // Plain dark gray background
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
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

            DashboardCard(
                text = "Low-Stock Notifications",
                icon = Icons.Default.Warning,
                backgroundColor = Color(0xFFF44336),
                onClick = onLowStockNotifications
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

fun Brush.toColor(): Color = Color(android.graphics.Color.parseColor("#FF4CAF50"))
