package com.example.farmerapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerInterfaceScreen() {
    // States for filters and price slider
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All Categories") }
    var selectedSortBy by remember { mutableStateOf("Price") }
    var sortOrder by remember { mutableStateOf("Ascending") }
    var priceRangeStart by remember { mutableStateOf(0f) }
    var priceRangeEnd by remember { mutableStateOf(50f) }

    // Category, sort, and order options
    val categories = listOf("All Categories", "Vegetables", "Fruits", "Seeds")
    val sortOptions = listOf("Price", "Popularity", "Newest Listings")
    val orderOptions = listOf("Ascending", "Descending")

    // Product data with valid image URLs
    val allProducts = listOf(
        Product(
            name = "Fresh Apples",
            price = 2.99f,
            location = "Orchard Farm",
            imageUrl = "https://pngimg.com/uploads/apple/apple_PNG12493.png" // Fixed URL
        ),
        Product(
            name = "Organic Carrots",
            price = 1.49f,
            location = "Green Valley",
            imageUrl = "https://i.scdn.co/image/ab6761610000e5ebf7b952107c126c561c52171e"
        ),
        Product(
            name = "Tomato Seeds",
            price = 4.99f,
            location = "Seed World",
            imageUrl = "https://images.unsplash.com/photo-1598032896924-8d8d5c1d1a3b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=400"
        ),
        Product(
            name = "Bananas",
            price = 1.99f,
            location = "Tropical Farms",
            imageUrl = "https://images.unsplash.com/photo-1574226516831-e1dff420e8f8?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=400"
        ),
        Product(
            name = "Pumpkins",
            price = 5.00f,
            location = "Harvest Heaven",
            imageUrl = "https://images.unsplash.com/photo-1506806732259-39c2d0268443?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&q=80&w=400"
        )
    )

    // Filtered Products Based on Price Range
    val filteredProducts = allProducts.filter {
        it.price in priceRangeStart..priceRangeEnd
    }

    // Main UI Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for products...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Dropdown
        DropdownMenuButton(
            label = "Category",
            options = categories,
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price Range Slider
        Text(
            text = "Filter by Price: \$${priceRangeStart.toInt()} - \$${priceRangeEnd.toInt()}",
            style = MaterialTheme.typography.bodyMedium
        )

        RangeSlider(
                value = priceRangeStart..priceRangeEnd, // Use ClosedFloatingPointRange for older versions
        onValueChange = { range ->
            priceRangeStart = range.start
            priceRangeEnd = range.endInclusive
        },
        valueRange = 0f..50f, // Define your slider range
        modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        // Sort By Dropdown
        DropdownMenuButton(
            label = "Sort By",
            options = sortOptions,
            selectedOption = selectedSortBy,
            onOptionSelected = { selectedSortBy = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Order Dropdown
        DropdownMenuButton(
            label = "Order",
            options = orderOptions,
            selectedOption = sortOrder,
            onOptionSelected = { sortOrder = it }
        )

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
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Green)
                )
                Text(
                    text = product.location,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun DropdownMenuButton(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("$label: $selectedOption")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}

data class Product(
    val name: String,
    val price: Float,
    val location: String,
    val imageUrl: String
)
