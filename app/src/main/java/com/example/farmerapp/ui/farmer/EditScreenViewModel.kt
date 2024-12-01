package com.example.farmerapp.ui.farmer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val farmerMarketRepository: FarmerMarketRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditItemUIState())
    val uiState: StateFlow<EditItemUIState> = _uiState

    private val productID = savedStateHandle.get<Int>("id") ?: -1

    init {
        _uiState.value = _uiState.value.copy(id = productID, updateState = ManageState.Loading)

        viewModelScope.launch {
            val product = farmerMarketRepository.getProduct(productID)
            _uiState.value = _uiState.value.copy(
                name = product.name,
                price = product.price,
                quantity = product.quantity,
                updateState = ManageState.Idle)
        }
    }

    fun updateNameField(entry: String) {
        _uiState.value = _uiState.value.copy(name = entry)
    }

    fun updateQuantityField(entry: String) {
        _uiState.value = _uiState.value.copy(quantity = entry)
    }

    fun updatePriceField(entry: String) {
        _uiState.value = _uiState.value.copy(price = entry)
    }

    fun updateProduct() {
        val name = _uiState.value.name
        val price = _uiState.value.price
        val quantity = _uiState.value.quantity
        val id = _uiState.value.id
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(updateState = ManageState.Loading)
            try {
                val response = farmerMarketRepository.updateProduct(id = id, name = name, price = price.toFloat(), quantity = quantity.toInt())

                if(response.isSuccessful) {
                    // send intent to the center of screen that writes updated successfully
                    val successMsg = "Update Successful"
                    _uiState.value = _uiState.value.copy(
                        updateState = ManageState.Success(successMsg)
                    )
                } else {
                    val errorMsg = if(response.code() == 400) "Empty fields" else "Unknown Error"
                    _uiState.value = _uiState.value.copy(
                        updateState = ManageState.Error(errorMsg)
                    )
                }
            } catch(e : Exception) {
                _uiState.value = _uiState.value.copy(
                    updateState = ManageState.Error(e.message ?: "Unknown Error exception")
                )
            }
        }
    }


}


data class EditItemUIState(
    val id: Int = -1,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val updateState: ManageState = ManageState.Idle
)

sealed class ManageState {
    object Idle : ManageState()
    object Loading : ManageState()
    data class Success(val successMsg: String) : ManageState()
    data class Error(val errorMsg: String) : ManageState()
}

fun Product.toUiState(): EditItemUIState = EditItemUIState(
    id = productID.toInt(),
    name = name,
    price = price,
    quantity = quantity,
)

