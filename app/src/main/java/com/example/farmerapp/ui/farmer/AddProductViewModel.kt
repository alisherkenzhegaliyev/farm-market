package com.example.farmerapp.ui.farmer

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class Product(
    val name: String,
    val category: String,
    val price: Double,
    val quantity: Int,
    val description: String
)

class AddProductViewModel : ViewModel() {

    // Mutable state for product fields
    var name = mutableStateOf("")
        private set
    var category = mutableStateOf("")
        private set
    var price = mutableStateOf("")
        private set
    var quantity = mutableStateOf("")
        private set
    var description = mutableStateOf("")
        private set

    // Event to handle the Save button click
    var isSaved = mutableStateOf(false)
        private set

    // Update methods for each field
    fun updateName(newName: String) {
        name.value = newName
    }

    fun updateCategory(newCategory: String) {
        category.value = newCategory
    }

    fun updatePrice(newPrice: String) {
        price.value = newPrice
    }

    fun updateQuantity(newQuantity: String) {
        quantity.value = newQuantity
    }

    fun updateDescription(newDescription: String) {
        description.value = newDescription
    }

    // Save Product Logic
    fun saveProduct() {
        viewModelScope.launch {
            val product = Product(
                name = name.value,
                category = category.value,
                price = price.value.toDoubleOrNull() ?: 0.0,
                quantity = quantity.value.toIntOrNull() ?: 0,
                description = description.value
            )
            // Logic to save the product (e.g., database, network call)
            println("Product saved: $product")
            isSaved.value = true
        }
    }
}
