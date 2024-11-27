package com.example.farmerapp.ui.farmer

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class Product(
    val name: String,
    val category: String,
    val price: Double,
    val quantity: Int,
    val description: String
)

data class AddProductUiState(
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val quantity: String = "",
    val description: String = "",
    val isSaved: Boolean = false
)

class AddProductViewModel(
    private val farmerMarketRepository: FarmerMarketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState = _uiState

    // Update methods for each field
    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updateCategory(newCategory: String) {
        _uiState.value = _uiState.value.copy(category = newCategory)
    }

    fun updatePrice(newPrice: String) {
        _uiState.value = _uiState.value.copy(price = newPrice)
    }

    fun updateQuantity(newQuantity: String) {
        _uiState.value = _uiState.value.copy(quantity = newQuantity)
    }

    fun updateDescription(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    // Save Product Logic
//    fun saveProduct() {
//        viewModelScope.launch {
//            val product = Product(
//                name = name.value,
//                category = category.value,
//                price = price.value.toDoubleOrNull() ?: 0.0,
//                quantity = quantity.value.toIntOrNull() ?: 0,
//                description = description.value
//            )
//            // Logic to save the product (e.g., database, network call)
//            println("Product saved: $product")
//            isSaved.value = true
//        }
//    }
}
