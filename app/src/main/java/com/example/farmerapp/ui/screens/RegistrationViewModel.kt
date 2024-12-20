package com.example.farmerapp.ui.screens
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.model.RequestResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

data class RegistrationUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
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
    val registrationStatus: String = "",
    val userType: String = "Buyer",
    val emailValidity: Boolean = false,
    val registrationState: AuthorizationState = AuthorizationState.Idle
)

class RegistrationViewModel(
    private val farmerMarketRepository: FarmerMarketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    // Update functions for shared and specific fields
    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            it.copy(email = email, errorMessage = if (isValid) "" else "Invalid email format", emailValidity = isValid)
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newPassword)
        }
    }

    fun switchToFarmerReg() {
        _uiState.update { it.copy(userType = "Farmer") }
    }

    fun switchToBuyerReg() {
        _uiState.update { it.copy(userType = "Buyer") }
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

    fun setInitialUi() {
        _uiState.update {
            it.copy(
                name = "",
                email = "",
                password = "",
                phoneNumber = "",
                farmAddress = "",
                farmSize = "",
                cropTypes = "",
                govtId = "",
                deliveryAddress = "",
                paymentMethod = "",
                errorMessage = "",
                registrationStatus = "",
                userType = "Buyer",
                emailValidity = false,
                registrationState = AuthorizationState.Idle,
            )
        }
    }
    // Registration logic
    suspend fun register() {
        _uiState.update { it.copy(registrationState = AuthorizationState.Loading) }

        try {
            val response: Response<RequestResponse>
            if(uiState.value.userType == "Farmer") {
                response = farmerMarketRepository.registerFarmer(
                    name = uiState.value.name,
                    email = uiState.value.email,
                    password = uiState.value.password,
                    phone = uiState.value.phoneNumber,
                    farmAddress = uiState.value.farmAddress,
                    farmSize = uiState.value.farmSize,
                    govtId = uiState.value.govtId,
                    cropTypes = uiState.value.cropTypes
                )
            } else {
                response = farmerMarketRepository.registerBuyer(
                    name = uiState.value.name,
                    email = uiState.value.email,
                    password = uiState.value.password,
                    phone = uiState.value.phoneNumber,
                    address = uiState.value.deliveryAddress,
                    ppm = uiState.value.paymentMethod
                )
            }

            if (response.isSuccessful) {
                _uiState.update {
                    it.copy(registrationState = AuthorizationState.Success(successMsg = response.body()?.message ?: "Successful"))
                }
            } else {
                val errorMsg = if(response.code() == 401) "Such user already exists" else "Fields are invalid"
                _uiState.update {
                    it.copy(registrationState = AuthorizationState.Error(errorMsg = errorMsg))
                }
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(registrationState = AuthorizationState.Error(errorMsg = e.message ?: "Unknown Error"))
            }
        }

    }

}
