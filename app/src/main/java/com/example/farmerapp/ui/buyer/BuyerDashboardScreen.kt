package com.example.farmerapp.ui.buyer

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
    var sortOrder by remember { mutableStateOf("Newest Listings") } // Default to "Newest Listings"
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }
    var isLocationDropdownExpanded by remember { mutableStateOf(false) }

    // Category and location options
    val categories = listOf("Vegetables", "Fruits", "Seeds")
    val locations = listOf("All Locations", "Orchard Farm", "Green Valley", "Seed World", "Tropical Farms", "Harvest Heaven")


    val orderOptions = listOf("Low to High", "High to Low")
    // Product data
    val allProducts = listOf(
        Product(
            name = "Fresh Apples",
            price = 2.99f,
            category = "Fruits",
            location = "Orchard Farm",
            quantity = 25,
            imageUrl = "https://images.unsplash.com/photo-1512578659172-63a4634c05ec?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            createdAt = System.currentTimeMillis() // Mark as newly added
        ),
        Product(
            name = "Organic Carrots",
            price = 1.49f,
            category = "Vegetables",
            location = "Green Valley",
            quantity = 40,
            imageUrl = "https://images.unsplash.com/photo-1447175008436-054170c2e979?q=80&w=1899&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            createdAt = System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000) // Added 8 days ago
        ),
        Product(
            name = "Tomato Seeds",
            price = 4.99f,
            category = "Seeds",
            location = "Seed World",
            quantity = 15,
            imageUrl = "https://images.unsplash.com/photo-1631262909868-d6f6ed8fbe7c?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            createdAt = System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000) // Added 1 day ago
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
        .let { products ->
            when (sortOrder) {
                "Newest Listings" -> products.sortedByDescending { it.createdAt }
                "Low to High" -> products.sortedBy { it.price }
                "High to Low" -> products.sortedByDescending { it.price }
                else -> products
            }
        }

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
        CategoryFilterDropdown(
            categories = categories,
            selectedCategories = selectedCategories,
            onCategorySelectionChange = { selectedCategories = it }
        )

        Spacer(modifier = Modifier.height(8.dp))
//        Spacer(modifier = Modifier.height(8.dp))

        // Collapsible Location Filter
        LocationFilterDropdown(
            locations = locations,
            selectedLocation = selectedLocation,
            onLocationSelectionChange = { selectedLocation = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PriceRangeSlider(
            priceRangeStart = priceRangeStart,
            priceRangeEnd = priceRangeEnd,
            onPriceRangeChange = { start, end ->
                priceRangeStart = start
                priceRangeEnd = end
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        SortOrderDropdown(
            sortOrder = sortOrder,
            onSortOrderChange = { sortOrder = it }
        )

        Spacer(modifier = Modifier.height(8.dp))


        // Product List
        LazyColumn {
            items(filteredProducts) { product ->
                ProductCard(product = product)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProductCard(product: Product) {
    val now = System.currentTimeMillis()
    val isNew = product.createdAt >= now - (7 * 24 * 60 * 60 * 1000) // New if within 7 days

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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    if (isNew) {
                        Text(
                            text = "New!",
                            color = GrassGreen,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
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
@Composable
fun SortOrderDropdown(
    sortOrder: String,
    onSortOrderChange: (String) -> Unit
) {
    val sortOptions = listOf("Newest Listings", "Low to High", "High to Low")
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Sort By: $sortOrder")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Sort Options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSortOrderChange(option)
                        expanded = false
                    },
                    text = { Text(option) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRangeSlider(
    priceRangeStart: Float,
    priceRangeEnd: Float,
    onPriceRangeChange: (Float, Float) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Price Range: \$${priceRangeStart.toInt()} - \$${priceRangeEnd.toInt()}",
            style = MaterialTheme.typography.bodyMedium
        )
        RangeSlider(
            value = priceRangeStart..priceRangeEnd,
            onValueChange = { range ->
                onPriceRangeChange(range.start, range.endInclusive)
            },
            valueRange = 0f..50f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = GrassGreen,
                activeTrackColor = DarkGreen
            )
        )
    }
}

@Composable
fun CategoryFilterDropdown(
    categories: List<String>,
    selectedCategories: Set<String>,
    onCategorySelectionChange: (Set<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Categories: ${if (selectedCategories.isEmpty()) "All" else selectedCategories.joinToString(", ")}")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
        }

        if (expanded) {
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
                                val updatedSelection = if (selectedCategories.contains(category)) {
                                    selectedCategories - category
                                } else {
                                    selectedCategories + category
                                }
                                onCategorySelectionChange(updatedSelection)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedCategories.contains(category),
                            onCheckedChange = {
                                val updatedSelection = if (it) selectedCategories + category else selectedCategories - category
                                onCategorySelectionChange(updatedSelection)
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
}

@Composable
fun LocationFilterDropdown(
    locations: List<String>,
    selectedLocation: String,
    onLocationSelectionChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GrassGreen),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Location: $selectedLocation")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
        }

        if (expanded) {
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
                                onLocationSelectionChange(location)
                                expanded = false // Collapse after selection
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLocation == location,
                            onClick = { onLocationSelectionChange(location) }
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
}

data class Product(
    val name: String,
    val price: Float,
    val category: String,
    val location: String,
    val quantity: Int,
    val imageUrl: String,
    val createdAt: Long // Timestamp for newest listings
)
