package com.example.farmerapp.ui.farmer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val farmerMarketRepository: FarmerMarketRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditItemUIState())
    val uiState: StateFlow<EditItemUIState> = _uiState

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val productID = savedStateHandle.get<Int>("id") ?: -1

    init {
        _uiState.update {
            it.copy()
        }
    }


}


data class EditItemUIState(
    val id: Int = -1,
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val quantity: String = "",
    val description: String = "",
    val updateState: ManageState = ManageState.Idle
)

sealed class ManageState {
    object Idle : ManageState()
    object Loading : ManageState()
    data class Success(val successMsg: String) : ManageState()
    data class Error(val errorMsg: String) : ManageState()
}
