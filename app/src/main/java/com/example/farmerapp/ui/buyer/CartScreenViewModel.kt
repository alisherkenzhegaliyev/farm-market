package com.example.farmerapp.ui.buyer
import CartItem
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class CartScreenViewModel : ViewModel() {
    // State for cart items
    var cartItems by mutableStateOf(
        listOf(
            CartItem(name = "Apple", price = 1.2, quantity = 3),
            CartItem(name = "Banana", price = 0.8, quantity = 2),
            CartItem(name = "Orange", price = 1.5, quantity = 1)
        )
    )

    // Function to update the quantity of a specific item
    fun updateQuantity(itemName: String, newQuantity: Int) {
        cartItems = cartItems.map {
            if (it.name == itemName) it.copy(quantity = newQuantity) else it
        }
    }

    // Function to calculate total price
    fun getTotalPrice(): Double {
        return cartItems.sumOf { it.price * it.quantity }
    }

    // Function to remove an item from the cart
    fun removeItem(itemName: String) {
        cartItems = cartItems.filter { it.name != itemName }
    }
}

