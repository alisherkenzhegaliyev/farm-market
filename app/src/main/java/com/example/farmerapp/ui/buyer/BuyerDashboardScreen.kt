package com.example.farmerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

// Define soothing green colors for the app
val LightGreen = Color(0xFFE8F5E9)
val GrassGreen = Color(0xFF81C784)
val DarkGreen = Color(0xFF388E3C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerInterfaceScreen() {
    // States for filters, search, price slider, category, and location
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }
    var selectedLocation by remember { mutableStateOf("All Locations") }
    var priceRangeStart by remember { mutableStateOf(0f) }
    var priceRangeEnd by remember { mutableStateOf(50f) }
    var sortOrder by remember { mutableStateOf("Low to High") }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isLocationDropdownExpanded by remember { mutableStateOf(false) }

    // Category and location options
    val categories = listOf("Vegetables", "Fruits", "Seeds")
    val locations = listOf("All Locations", "Orchard Farm", "Green Valley", "Seed World", "Tropical Farms", "Harvest Heaven")

    // Product data
    val allProducts = listOf(
        Product(
            name = "Fresh Apples",
            price = 2.99f,
            category = "Fruits",
            location = "Orchard Farm",
            quantity = 25,
            imageUrl = "https://pngimg.com/uploads/apple/apple_PNG12493.png"
        ),
        Product(
            name = "Organic Carrots",
            price = 1.49f,
            category = "Vegetables",
            location = "Green Valley",
            quantity = 40,
            imageUrl = "https://i.scdn.co/image/ab6761610000e5ebf7b952107c126c561c52171e"
        ),
        Product(
            name = "Tomato Seeds",
            price = 4.99f,
            category = "Seeds",
            location = "Seed World",
            quantity = 15,
            imageUrl = "https://images.unsplash.com/photo-1598032896924-8d8d5c1d1a3b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=400"
        )
    )

    // Filtered Products Based on Search, Price, Category, and Location
    val filteredProducts = allProducts
        .filter {
            it.name.contains(searchQuery, ignoreCase = true) &&
                    (selectedCategories.isEmpty() || selectedCategories.contains(it.category)) &&
                    it.price in priceRangeStart..priceRangeEnd &&
                    (selectedLocation == "All Locations" || it.location == selectedLocation)
        }
        .sortedBy { if (sortOrder == "Low to High") it.price else -it.price }

    // Main UI Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(LightGreen) // Background color for the app
    ) {
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for products...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Collapsible Category Filter with Checkboxes
        Column {
            Button(
                onClick = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Categories: ${if (selectedCategories.isEmpty()) "All" else selectedCategories.joinToString(", ")}")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
            }

            if (isCategoryDropdownExpanded) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 8.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    items(categories) { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    if (selectedCategories.contains(category)) {
                                        selectedCategories = selectedCategories - category
                                    } else {
                                        selectedCategories = selectedCategories + category
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedCategories.contains(category),
                                onCheckedChange = {
                                    if (it) selectedCategories = selectedCategories + category
                                    else selectedCategories = selectedCategories - category
                                }
                            )
                            Text(
                                text = category,
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Collapsible Location Filter
        Column {
            Button(
                onClick = { isLocationDropdownExpanded = !isLocationDropdownExpanded },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Location: $selectedLocation")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
            }

            if (isLocationDropdownExpanded) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 8.dp)
                        .background(Color.White, RoundedCornerShape(8.dp))
                ) {
                    items(locations) { location ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedLocation = location
                                    isLocationDropdownExpanded = false // Collapse after selection
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedLocation == location,
                                onClick = { selectedLocation = location }
                            )
                            Text(
                                text = location,
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product List
        LazyColumn {
            items(filteredProducts) { product ->
                ProductCard(product = product)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(model = product.imageUrl),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Product Details
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "\$${product.price}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = GrassGreen)
                )
                Text(
                    text = "Available: ${product.quantity} kg",
                    style = MaterialTheme.typography.bodyMedium.copy(color = DarkGreen)
                )
                Text(
                    text = product.location,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}

data class Product(
    val name: String,
    val price: Float,
    val category: String,
    val location: String,
    val quantity: Int,
    val imageUrl: String
)
