//package com.example.farmerapp.ui.buyer
//
//import com.google.android.gms.analytics.ecommerce.Product
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flow
//
//class BuyerRepository {
//    // Replace these with actual API or database calls
//    fun getProducts(): Flow<List<Product>> = flow {
//        emit(
//            listOf(
//                Product("1", "Tomato", 2.0, 100, "Farm A", "Fresh tomatoes", "Vegetables", 5),
//                Product("2", "Apple", 1.5, 200, "Farm B", "Crisp apples", "Fruits", 10)
//            )
//        )
//    }
//
//    fun searchProducts(query: String): Flow<List<Product>> = flow {
//        // Mock search implementation
//        emit(
//            getProducts().first().filter { it.name.contains(query, ignoreCase = true) }
//        )
//    }
//
//    fun getProductById(productId: String): Flow<Product?> = flow {
//        emit(getProducts().first().find { it.id == productId })
//    }
//}
