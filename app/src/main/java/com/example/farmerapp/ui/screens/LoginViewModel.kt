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

    fun updatePasswordField(entry: String) {
        uiState.value = uiState.value.copy(passwordEntry = entry)
    }

    fun updateVisiblity() {
        val pswVisiblity = uiState.value.passwordVisible
        uiState.value = uiState.value.copy(passwordVisible = !pswVisiblity)
    }




}

data class LoginUiState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val passwordVisible: Boolean = false
)