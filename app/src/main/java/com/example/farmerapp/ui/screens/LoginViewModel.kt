package com.example.farmerapp.ui.screens

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel(
    farmMarketRepository:  FarmerMarketRepository,
) : ViewModel() {

    var uiState = MutableStateFlow(LoginUiState())


    fun updateEmailField(entry: String) {
        uiState.value = uiState.value.copy(emailEntry = entry)
    }



}

data class LoginUiState(
    val emailEntry: String = "",
    val passwordEntry: String = ""
)