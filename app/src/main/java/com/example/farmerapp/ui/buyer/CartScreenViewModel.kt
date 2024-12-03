package com.example.farmerapp.ui.buyer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartScreenViewModel(
    farmerMarketRepository: FarmerMarketRepository,
    sessionManager: SessionManager
) : ViewModel() {
    // State for cart items
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState

    init {
        viewModelScope.launch {
        Log.i("CartScreenViewModel", "Inside CartScreen viewModel")
        _uiState.value = _uiState.value.copy(state = BuyerState.Loading)
            _uiState.value = _uiState.value.copy(state = BuyerState.Loading)
            try {
                val cartItems = farmerMarketRepository.getCartItems(sessionManager.getUserId().toInt())
                var totalPrice = 0f
                val productList = cartItems.map { cartItem ->
                    val product = farmerMarketRepository.getProduct(cartItem.productid.toInt())
                    CartItem(pr = product, quantity = cartItem.quantity.toInt(), price = product.price.toFloat())
                }
                productList.forEach {
                    Log.i("CartScreenViewModel", "Product: ${it.price * it.quantity}")
                    totalPrice += it.price * it.quantity
                }
                _uiState.value = _uiState.value.copy(cartItems = productList, state = BuyerState.Success("Success"), totalPrice = totalPrice)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = BuyerState.Error("Failed to fetch cart items"))
            }
        }
    }

}


data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Float = 0f,
    val state: BuyerState = BuyerState.Loading
)

data class CartItem(
    val pr: Product,
    val quantity: Int,
    val price: Float
)
