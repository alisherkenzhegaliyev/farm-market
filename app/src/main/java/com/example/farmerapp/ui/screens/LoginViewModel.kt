package com.example.farmerapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val farmMarketRepository:  FarmerMarketRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    var uiState = _uiState

    fun setLoginInitial() {
        _uiState.value = _uiState.value.copy(
            chosenRole = _uiState.value.chosenRole,
            emailEntry = "",
            passwordEntry = "",
            passwordVisible = false,
            loginState = AuthorizationState.Idle
        )
    }

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

    suspend fun login() {
        val email = _uiState.value.emailEntry
        val password = _uiState.value.passwordEntry
        val role = _uiState.value.chosenRole.name

        _uiState.value = _uiState.value.copy(loginState = AuthorizationState.Loading)

        try {
            val response = farmMarketRepository.login(
                email,
                password,
                role
            )

            if(response.isSuccessful) {
                val successMsg = "Login Successful"
                _uiState.value = _uiState.value.copy(
                    loginState = AuthorizationState.Success(successMsg)
                )

                val userId = response.body()?.message ?: "-1"
                val userName = response.body()?.name ?: "unknown"
                Log.i("LoginViewModel", "session manager with values $userId $userName ${_uiState.value.chosenRole.name}")

                sessionManager.saveUserDetail(
                    userId = userId,
                    userType = _uiState.value.chosenRole.name,
                    userName = userName
                )


            } else  {
                val errorMsg = if(response.code() == 401) "Invalid Password" else if(response.code() == 404) "Given User does not exist" else "Unknown Error"
                Log.i("LoginViewModel", "In error")
                _uiState.value = _uiState.value.copy(
                    loginState = AuthorizationState.Error(errorMsg)
                )
            }
        } catch(e: Exception) {
            _uiState.value = _uiState.value.copy(
                loginState = AuthorizationState.Error(e.message ?: "Unknown Error exception")
            )
        }

    }
}

data class LoginUiState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val passwordVisible: Boolean = false,
    val chosenRole: Role = Role.Farmer,
    val loginState: AuthorizationState = AuthorizationState.Idle
)

enum class Role {
    Buyer,
    Farmer
}

sealed class AuthorizationState {
    object Idle : AuthorizationState()
    object Loading : AuthorizationState()
    data class Success(val successMsg: String) : AuthorizationState()
    data class Error(val errorMsg: String) : AuthorizationState()
}

