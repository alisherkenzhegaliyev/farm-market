package com.example.farmerapp.ui.buyer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    private val sessionManager: SessionManager
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
               cartItems.forEach {
                   totalPrice += it.productprice.toFloat() * it.quantity
               }
                _uiState.value = _uiState.value.copy(cartItems = cartItems, state = BuyerState.Success("Success"), totalPrice = totalPrice)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(state = BuyerState.Error("Failed to fetch cart items"))
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(state = BuyerState.Loading)
            }
            try {
                _uiState.value.cartItems.forEach {
                    val response = farmerMarketRepository.addOrder(Order(
                        farmerid = it.farmerid,
                        buyerid = it.buyerid,
                        orderstatus = "Pending",
                        totalamount = it.quantity,
                        totalprice = it.productprice.toFloat() * it.quantity,
                        productname = it.productname
                    ))
                    if(response.isSuccessful) {
                        farmerMarketRepository.deleteCartItem(it.cartid!!)
                    }

                }
                _uiState.update {
                    it.copy(cartItems = emptyList(), state = BuyerState.Success("Order Placed"), totalPrice = 0f)
                }
            } catch(e : Exception) {
                _uiState.update {
                    it.copy(state = BuyerState.Error(e.message ?: "Unknown Error"))
                }
            }
        }
    }

    fun removeItemFromCart(cartInstance: Cart) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(state = BuyerState.Loading)
            }

            try {
                val response = farmerMarketRepository.deleteCartItem(cartInstance.cartid!!)
                if(response.isSuccessful) {
                    val productCurrentQuantity =
                        farmerMarketRepository.getProduct(cartInstance.productid).quantity
                    farmerMarketRepository.updateProduct(
                        cartInstance.productid,
                        cartInstance.productname,
                        cartInstance.productprice.toFloat(),
                        (cartInstance.quantity + productCurrentQuantity.toInt())
                    )
                    val updatedCartItems = _uiState.value.cartItems.filter {
                        it.cartid != cartInstance.cartid
                    }
                    var totalPrice = 0f
                    updatedCartItems.forEach {
                        totalPrice += it.productprice.toFloat() * it.quantity
                    }

                    _uiState.update {
                        it.copy(cartItems = updatedCartItems, state = BuyerState.Success("Deleted from cart"), totalPrice = totalPrice)
                    }
                } else {
                    _uiState.update {
                        it.copy(state = BuyerState.Error("Failed to delete from cart"))
                    }
                }
            } catch(e : Exception) {
                _uiState.update {
                    it.copy(state = BuyerState.Error(e.message ?: "Unknown Error"))
                }
            }
        }
    }

}


data class CartUiState(
    val cartItems: List<Cart> = emptyList(),
    val totalPrice: Float = 0f,
    val state: BuyerState = BuyerState.Loading,
)
