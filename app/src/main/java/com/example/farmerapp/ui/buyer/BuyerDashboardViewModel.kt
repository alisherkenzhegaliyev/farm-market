package com.example.farmerapp.ui.buyer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BuyerDashboardViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private var requestState = MutableStateFlow(RequestState())
    val request = requestState

    val uiState: StateFlow<ProductListUiState> =
        farmerMarketRepository.getAllProducts().map { ProductListUiState(productList = it.map { product -> ProductWithCounter(product) }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ProductListUiState()
            )

    fun addToCart(pr: ProductWithCounter, counter : Int) {
        val toAdd = Cart(
            productid = pr.pr.productID,
            quantity = counter,
            buyerid = sessionManager.getUserId().toInt(),
            farmerid = pr.pr.farmerID,
            status = "active",
        )

        viewModelScope.launch {
            requestState.value = requestState.value.copy(requestState = BuyerState.Loading)
            try {
                val response = farmerMarketRepository.addToCart(toAdd)
                if (response.isSuccessful) {
                    requestState.value = requestState.value.copy(requestState = BuyerState.Success("Successfully added to the cart"))
                    farmerMarketRepository.updateProduct(
                        id = pr.pr.productID,
                        name = pr.pr.name,
                        price = pr.pr.price.toFloat(),
                        quantity = pr.pr.quantity.toInt() - counter)
                } else {
                    requestState.value = requestState.value.copy(requestState = BuyerState.Error("Error adding to cart"))
                }

            } catch(e : Exception) {
                requestState.value = requestState.value.copy(requestState = BuyerState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

}

sealed class BuyerState {
    object Idle : BuyerState()
    object Loading : BuyerState()
    data class Success(val message: String) : BuyerState()
    data class Error(val message: String) : BuyerState()
}

data class RequestState(
    val requestState: BuyerState = BuyerState.Idle
)

data class ProductListUiState(
    val productList: List<ProductWithCounter> = listOf(),
)

data class ProductWithCounter(
    val pr: Product
)