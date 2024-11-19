//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Card
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.farmerapp.data.Product
//
//@Composable
//fun BuyerDashboardScreen(navController: NavController) {
//    // Mock data for products
//    val products = listOf(
//        Product("1", "Tomato", 2.0, 100, "Farm A", "Fresh tomatoes", "Vegetables", 5),
//        Product("2", "Apple", 1.5, 200, "Farm B", "Crisp apples", "Fruits", 10),
//        Product("3", "Carrot", 1.0, 150, "Farm C", "Organic carrots", "Vegetables", 8),
//    )
//
//    LazyColumn {
//        products.groupBy { it.category }.forEach { (category, products) ->
//            item {
//                Text(
//                    category,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(8.dp)
//                )
//            }
//            items(products) { product ->
//                ProductCard(product) {
//                    // Navigate to product detail screen
//                    navController.navigate("productDetail/${product.id}")
//                }
//            }
//
//
//            items(products) { product ->
//                ProductCard(product) {
//                    // Navigate to product detail screen
//                    navController.navigate("productDetail/${product.id}")
//                }
//            }
//        }
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProductCard(product: Product, onClick: () -> Unit) {
//    // Display product details
//    // Handle click event
//    Card(
//        onClick = onClick,
//        modifier = Modifier.padding(8.dp)
//    ) {
//        Text(product.name)
//    }
//}
//
//
//
