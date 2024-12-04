package com.example.farmerapp.ui.buyer

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.farmerapp.model.Product
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

// Define soothing green colors for the app
val LightGreen = Color(0xFFE8F5E9)
val GrassGreen = Color(0xFF81C784)
val DarkGreen = Color(0xFF388E3C)


@Composable
fun BuyerHomeScreen(
    navController: NavController // Pass NavController to FarmerDashboardScreen
) {
    Scaffold(
        topBar = {  },
        content = { padding ->
            BuyerInterfaceScreen(
                modifier = Modifier.padding(padding),
            )
        },
        bottomBar = { BuyerBottomBar(navController = navController) } // Add BottomNavigationBar here
    )
}

data class NavigationItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)

@Composable
fun BuyerBottomBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Log.i("BuyerBottomBar", "Current Route: $currentRoute")
    NavigationBar(
        containerColor = Color.White, // Background color of the navigation bar
        tonalElevation = 4.dp // Elevation effect
    ) {
        val navItems = listOf(
            NavigationItem("Home", Icons.Default.Home, "buyer_home"),
            NavigationItem("Chats", Icons.Filled.Chat, "chat_listing"),
            NavigationItem("Profile", Icons.Default.Person, "profile"),
            NavigationItem("Cart", Icons.Default.ShoppingCart, "cart"),
            NavigationItem("Orders", Icons.Default.History, "order")
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
                        navController.navigate(item.route)
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
fun BuyerInterfaceScreen(
    modifier: Modifier = Modifier,
    viewModel: BuyerDashboardViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {

    Log.i("BuyerInterfaceScreen", "Entered BuyerInterfaceScreen")
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
//    val allProducts = listOf(
//        Product(
//            name = "Fresh Apples",
//            price = 2.99f,
//            category = "Fruits",
//            location = "Orchard Farm",
//            quantity = 25,
//            imageUrl = "https://images.unsplash.com/photo-1512578659172-63a4634c05ec?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//            createdAt = System.currentTimeMillis() // Mark as newly added
//        ),
//        Product(
//            name = "Organic Carrots",
//            price = 1.49f,
//            category = "Vegetables",
//            location = "Green Valley",
//            quantity = 40,
//            imageUrl = "https://images.unsplash.com/photo-1447175008436-054170c2e979?q=80&w=1899&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//            createdAt = System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000) // Added 8 days ago
//        ),
//        Product(
//            name = "Tomato Seeds",
//            price = 4.99f,
//            category = "Seeds",
//            location = "Seed World",
//            quantity = 15,
//            imageUrl = "https://images.unsplash.com/photo-1631262909868-d6f6ed8fbe7c?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
//            createdAt = System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000) // Added 1 day ago
//        )
//    )

    val uiState = viewModel.uiState.collectAsState()
    val allProducts: List<ProductWithCounter> = uiState.value.productList

    Log.i("BuyerInterfaceScreen", "Products fetched, and they are: $allProducts")

    // Filtered Products Based on Search, Price, Category, and Location
    val filteredProducts = allProducts
        .filter {
            it.pr.name.contains(searchQuery, ignoreCase = true) &&
//                    (selectedCategories.isEmpty() || selectedCategories.contains(it.category)) &&
                    it.pr.price.toFloat() in priceRangeStart..priceRangeEnd
//                    && (selectedLocation == "All Locations" || it.location == selectedLocation)
        }
        .let { products ->
            when (sortOrder) {
                "Low to High" -> products.sortedBy { it.pr.price.toFloat() }
                "High to Low" -> products.sortedByDescending { it.pr.price.toFloat() }
                else -> products
            }
        }
    Log.i("BuyerInterfaceScreen", "Filtered products: $filteredProducts")

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

//        Spacer(modifier = Modifier.height(8.dp))

        // Collapsible Category Filter with Checkboxes
//        CategoryFilterDropdown(
//            categories = categories,
//            selectedCategories = selectedCategories,
//            onCategorySelectionChange = { selectedCategories = it }
//        )

//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Collapsible Location Filter
//        LocationFilterDropdown(
//            locations = locations,
//            selectedLocation = selectedLocation,
//            onLocationSelectionChange = { selectedLocation = it }
//        )
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
                ProductCard(product = product, viewModel::addToCart, viewModel::addChat)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProductCard(
    product: ProductWithCounter,
    addToCart: (ProductWithCounter, Int) -> Unit,
    sendChat: (Int) -> Unit
) {
    var counter by remember { mutableStateOf(0) }

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
            val checkProductLength = product.pr.image?.length ?: 0
            if (checkProductLength > 10) {
                Image(
                    painter = rememberAsyncImagePainter(model = product.pr.image),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

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
                        text = product.pr.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "$${product.pr.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkGreen
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Available: ${product.pr.quantity}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Column for buttons: Counter, Add to Cart, Add Chat
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                // Counter Adjustment (Plus and Minus Buttons)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (counter > 0) counter -= 1
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease Quantity",
                            tint = DarkGreen
                        )
                    }

                    Text(
                        text = "$counter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = {
                            if (counter < product.pr.quantity.toInt()) counter += 1
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase Quantity",
                            tint = DarkGreen
                        )
                    }
                }

                // Add to Cart Button
                Button(
                    onClick = { addToCart(product, counter) },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
                ) {
                    Text(text = "Add to Cart", color = Color.White, fontSize = 14.sp)
                }

                // Add Chat Button
                IconButton(
                    onClick = { sendChat(product.pr.farmerID) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "Start Chat",
                        tint = DarkGreen
                    )
                }
            }
        }
    }
}
@Composable
fun SortOrderDropdown(
    sortOrder: String,
    onSortOrderChange: (String) -> Unit
) {
//    val sortOptions = listOf("Newest Listings", "Low to High", "High to Low")
    val sortOptions = listOf("Low to High", "High to Low")
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

