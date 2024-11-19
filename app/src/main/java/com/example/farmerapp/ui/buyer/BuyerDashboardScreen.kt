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
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All Categories") }
    var selectedSortBy by remember { mutableStateOf("Price") }
    var sortOrder by remember { mutableStateOf("Ascending") }
    val categories = listOf("All Categories", "Vegetables", "Fruits", "Seeds")
    val sortOptions = listOf("Price", "Popularity", "Newest Listings")
    val orderOptions = listOf("Ascending", "Descending")

    // Product data with real images
    val products = listOf(
        Product(
            name = "Fresh Apples",
            price = "$2.99/kg",
            location = "Orchard Farm",
            imageUrl = "https://pngimg.com/uploads/apple_logo/apple_logo_PNG19666.png"
        ),
        Product(
            name = "Organic Carrots",
            price = "$1.49/kg",
            location = "Green Valley",
            imageUrl = "https://images.unsplash.com/photo-1582515073490-399813c3a4b0"
        ),
        Product(
            name = "Tomato Seeds",
            price = "$4.99/pack",
            location = "Seed World",
            imageUrl = "https://images.unsplash.com/photo-1598032896924-8d8d5c1d1a3b"
        ),
        Product(
            name = "Bananas",
            price = "$1.99/kg",
            location = "Tropical Farms",
            imageUrl = "https://unsplash.com/photos/three-ripe-bananas-on-a-yellow-background-gcJQzVTpcoY"
        ),
        Product(
            name = "Pumpkins",
            price = "$5.00/each",
            location = "Harvest Heaven",
            imageUrl = "https://images.unsplash.com/photo-1506806732259-39c2d0268443"
        )
    )

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

        // Filters: Category Dropdown
        DropdownMenuButton(
            label = "Category",
            options = categories,
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filters: Sort By Dropdown
        DropdownMenuButton(
            label = "Sort By",
            options = sortOptions,
            selectedOption = selectedSortBy,
            onOptionSelected = { selectedSortBy = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Filters: Order Dropdown
        DropdownMenuButton(
            label = "Order",
            options = orderOptions,
            selectedOption = sortOrder,
            onOptionSelected = { sortOrder = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product List
        LazyColumn {
            items(products) { product ->
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
                    text = product.price,
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
                            text = { Text(option) } // Pass Text composable as the text parameter
                        )
                    }
                }
            }
        }

    }
}

data class Product(
    val name: String,
    val price: String,
    val location: String,
    val imageUrl: String
)
