package com.example.farmerapp.ui.buyer
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Product
import kotlinx.coroutines.launch

class CartScreenViewModel(
    farmerMarketRepository: FarmerMarketRepository,
    sessionManager: SessionManager
) : ViewModel() {
    // State for cart items
    private val _uiState = mutableStateOf(CartUiState())
    val uiState = _uiState

    init {
        _uiState.value = _uiState.value.copy(state = BuyerState.Loading)

        viewModelScope.launch {
            try {
                val cartItems = farmerMarketRepository.getCartItems(sessionManager.getUserId().toInt())
                var totalPrice = 0f
                val productList = cartItems.map { cartItem ->
                    val product = farmerMarketRepository.getProduct(cartItem.productId)
                    CartItem(pr = product, quantity = cartItem.quantity, price = product.price.toFloat())
                }
                productList.forEach {
                    totalPrice += it.price * it.quantity
                }
                _uiState.value = _uiState.value.copy(cartItems = productList, state = BuyerState.Success("Success"))
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = BuyerState.Error("Failed to fetch cart items"))
            }
        }
    }

}


data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Float = 0f,
    val state: BuyerState = BuyerState.Idle
)

data class CartItem(
    val pr: Product,
    val quantity: Int,
    val price: Float
)
