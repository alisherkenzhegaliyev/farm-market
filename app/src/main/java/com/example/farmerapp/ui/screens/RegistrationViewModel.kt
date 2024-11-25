package com.example.farmerapp.ui.screens
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class RegistrationUiState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    // Farmer-specific fields
    val farmAddress: String = "",
    val farmSize: String = "",
    val cropTypes: String = "",
    val govtId: String = "",
    // Buyer-specific fields
    val deliveryAddress: String = "",
    val paymentMethod: String = "",
    // Status
    val errorMessage: String = "",
    val registrationStatus: String = ""
)

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    // Update functions for shared and specific fields
    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            it.copy(email = email, errorMessage = if (isValid) "" else "Invalid email format")
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    // Farmer-specific updates
    fun updateFarmAddress(farmAddress: String) {
        _uiState.update { it.copy(farmAddress = farmAddress) }
    }

    fun updateFarmSize(farmSize: String) {
        _uiState.update { it.copy(farmSize = farmSize) }
    }

    fun updateCropTypes(cropTypes: String) {
        _uiState.update { it.copy(cropTypes = cropTypes) }
    }

    fun updateGovtId(govtId: String) {
        _uiState.update { it.copy(govtId = govtId) }
    }

    // Buyer-specific updates
    fun updateDeliveryAddress(deliveryAddress: String) {
        _uiState.update { it.copy(deliveryAddress = deliveryAddress) }
    }

    fun updatePaymentMethod(paymentMethod: String) {
        _uiState.update { it.copy(paymentMethod = paymentMethod) }
    }

    // Registration logic
    fun registerFarmer() {
        val currentState = _uiState.value
        if (currentState.name.isBlank() || currentState.email.isBlank() || currentState.phoneNumber.isBlank() ||
            currentState.farmAddress.isBlank() || currentState.farmSize.isBlank() || currentState.cropTypes.isBlank() || currentState.govtId.isBlank()
        ) {
            _uiState.update { it.copy(errorMessage = "All fields are required for farmer registration.") }
        } else {
            _uiState.update { it.copy(registrationStatus = "Farmer registration successful. Verification email/SMS sent.") }
        }
    }

    fun registerBuyer() {
        val currentState = _uiState.value
        if (currentState.name.isBlank() || currentState.email.isBlank() || currentState.phoneNumber.isBlank() ||
            currentState.deliveryAddress.isBlank() || currentState.paymentMethod.isBlank()
        ) {
            _uiState.update { it.copy(errorMessage = "All fields are required for buyer registration.") }
        } else {
            _uiState.update { it.copy(registrationStatus = "Buyer registration successful. Verification email/SMS sent.") }
        }
    }
}
