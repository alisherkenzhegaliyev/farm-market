package com.example.farmerapp.ui.buyer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    val userType = sessionManager.getUserType()
    val userId = sessionManager.getUserId()

    private val _orders = MutableStateFlow(OrdersUiState())
    val orders = _orders

    init {
        getOrders()
    }

    fun getOrders() {
        viewModelScope.launch {
            _orders.update {it.copy(isLoading = true)}
            try {
                val orders = farmerMarketRepository.getOrders(userId.toInt(), userType!!)
                _orders.update {it.copy(orders = orders, isLoading = false, error = "")}
            } catch (e: Exception) {
                _orders.update {it.copy(error = e.message ?: "Unknown error", isLoading = false)}
            }
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            _orders.update { it.copy(isLoading = true) }
            try {
                val response = farmerMarketRepository.deleteOrder(id)
                if (response.isSuccessful) {
                    _orders.update {
                        it.copy(isLoading = false, error = "")
                    }
                    getOrders()
                } else {
                    _orders.update {
                        it.copy(isLoading = false, error = response.message())
                    }
                }
            } catch (e: Exception) {
                _orders.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }

            }
        }
    }

    fun updateOrder(order: Order, accepted: Boolean) {
        viewModelScope.launch {
            _orders.update { it.copy(isLoading = true) }
            try {
                val response = farmerMarketRepository.updateOrder(
                    order = order.copy(orderstatus = if(accepted) "Completed" else "Rejected")
                )
                if (response.isSuccessful) {
                    _orders.update {
                        it.copy(isLoading = false, error = "")
                    }
                    getOrders()
                } else {
                    _orders.update {
                        it.copy(isLoading = false, error = response.message())
                    }
                }
            } catch (e: Exception) {
                _orders.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }

            }
        }

    }

}

data class OrdersUiState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
)