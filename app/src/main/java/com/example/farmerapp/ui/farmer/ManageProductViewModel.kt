package com.example.farmerapp.ui.farmer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ManageProductViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    sessionManager: SessionManager
) : ViewModel() {

    private val farmerID = sessionManager.getUserId()

    private val _uiState = MutableStateFlow(ManageProductsUiState())
    val uiState = _uiState
        .onStart { getFarmerProducts() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ManageProductsUiState()
        )


    private fun getFarmerProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(manageState = ManageState.Loading)
            farmerMarketRepository.getFarmersProduct(farmerID.toInt()).collect { products ->
                _uiState.value = _uiState.value.copy(productList = products, manageState = ManageState.Success("Success"))
            }
        }
    }

    fun deleteProduct(productID: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(manageState = ManageState.Loading)
            try {
                val response = farmerMarketRepository.deleteProduct(productID)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(manageState =ManageState.Success("Deleted successfully"))
                    getFarmerProducts()
                } else {
                    _uiState.value = _uiState.value.copy(manageState = ManageState.Error("Failed to delete"))
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(manageState = ManageState.Error("Failed to delete exception"))
            }
        }

    }

}
data class ManageProductsUiState(
    val productList: List<Product> = listOf(),
    val manageState: ManageState = ManageState.Idle,

)