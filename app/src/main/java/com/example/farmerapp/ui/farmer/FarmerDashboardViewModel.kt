package com.example.farmerapp.ui.farmer

import androidx.lifecycle.ViewModel
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class FarmerDashboardUiState(
    val errorMessage: String = "",
    val isProcessing: Boolean = false
)

class FarmerDashboardViewModel(
    private val farmerRepository: FarmerMarketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FarmerDashboardUiState())
    val uiState: StateFlow<FarmerDashboardUiState> = _uiState

    fun startProcessing() {
        _uiState.update { it.copy(isProcessing = true) }
    }

    fun stopProcessing() {
        _uiState.update { it.copy(isProcessing = false) }
    }

    fun setError(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = "") }
    }
}
