package com.example.farmerapp.ui.farmer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val farmerMarketRepository: FarmerMarketRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditItemUIState())
    val uiState: StateFlow<EditItemUIState> = _uiState

    private val productID = savedStateHandle.get<Int>("id") ?: -1

    init {
        // Fetch the product details from the repository and update the UI state
        //Imagine we have ready repository so write viewmodel implementation here
//        if(productID != -1) {
//            viewModelScope.launch {
//                val product = farmerMarketRepository.getProductById(productID)
//                _uiState.update {
//                    it.copy(
//                        name = product.name,
//                        category = product.category,
//                        price = product.price.toString(),
//                        quantity = product.quantity.toString(),
//                }
//            }
//        }
        _uiState.update {
            it.copy(id = productID)
        }
    }


}


data class EditItemUIState(
    val id: Int = -1,
    val name: String = "",
    val category: String = "",
    val price: String = "",
    val quantity: String = "",
    val description: String = ""
)
