package com.example.farmerapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val farmMarketRepository:  FarmerMarketRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    var uiState = _uiState


    fun updateEmailField(entry: String) {
        _uiState.value = _uiState.value.copy(emailEntry = entry)
    }

    fun updatePasswordField(entry: String) {
        _uiState.value = _uiState.value.copy(passwordEntry = entry)
    }

    fun updateVisibility() {
        val pswVisibility = _uiState.value.passwordVisible
        _uiState.value = _uiState.value.copy(passwordVisible = !pswVisibility)
    }

    fun updateRole(role: Role) {
        _uiState.value = _uiState.value.copy(chosenRole = role)
    }

    fun login() {
        val email = _uiState.value.emailEntry
        val password = _uiState.value.passwordEntry
        val role = _uiState.value.chosenRole.name

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loginState = LoginState.Loading)

            try {
                val response = farmMarketRepository.login(
                    email,
                    password,
                    role
                )

                if(response.body()?.message != null) {
                    _uiState.value = _uiState.value.copy(
                        loginState = LoginState.Success(response.body()?.message ?: "Unknown Error for not null msg")
                    )
                } else {
                    Log.i("LoginViewModel", "IN error")
                    _uiState.value = _uiState.value.copy(
                        loginState = LoginState.Error(response.body()?.error ?: "Unknown Error")
                    )
                }
            } catch(e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loginState = LoginState.Error(e.message ?: "Unknown Error exception")
                )
            }
        }

    }
}

data class LoginUiState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val passwordVisible: Boolean = false,
    val chosenRole: Role = Role.Farmer,
    val loginState: LoginState = LoginState.Idle
)

enum class Role {
    Buyer,
    Farmer
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val successMsg: String) : LoginState()
    data class Error(val errorMsg: String) : LoginState()
}

