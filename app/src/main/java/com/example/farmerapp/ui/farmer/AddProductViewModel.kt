package com.example.farmerapp.ui.farmer

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.AddUpdateRequest
import com.example.farmerapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


data class AddProductUiState(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val farmerId: Int = -1,
    val isSaved: Boolean = false,
    val isLoading: Boolean = false,
    val savePressed: Boolean = false,
)

class AddProductViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    sessionManager: SessionManager
) : ViewModel() {

    val farmerID = sessionManager.getUserId()

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState = _uiState

    // Update methods for each field
    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updatePrice(newPrice: String) {
        Log.i("newprice", newPrice)
        _uiState.value = _uiState.value.copy(price = newPrice)
    }

    fun updateQuantity(newQuantity: String) {
        _uiState.value = _uiState.value.copy(quantity = newQuantity)
    }

    fun addProduct() {
        Log.i("HEREValue", farmerID)
        _uiState.value = _uiState.value.copy(isLoading = true, savePressed = true)
        viewModelScope.launch {
            Log.i("addProduct", "${_uiState.value.name} ${_uiState.value.price} ${_uiState.value.quantity} ${farmerID}")
            try {
                val response = farmerMarketRepository.addProduct(
                    AddUpdateRequest(
                        name = _uiState.value.name,
                        price = _uiState.value.price.toFloat(),
                        quantity = _uiState.value.quantity.toInt(),
                        farmerID = farmerID.toInt(),
                    )
                )
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(isSaved = true, isLoading = false)
                    Log.i("HEREValue", "HEREValue...")
                } else {
                    _uiState.value = _uiState.value.copy(isSaved = false, isLoading = false)
                }
            } catch (e : Exception) {
                _uiState.value = _uiState.value.copy(isSaved = false, isLoading = false)
            }
        }
    }
}
