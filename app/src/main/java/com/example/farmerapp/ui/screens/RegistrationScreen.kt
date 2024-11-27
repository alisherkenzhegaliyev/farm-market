package com.example.farmerapp.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onNavigateToLogin: () -> Unit
) {
    // State to track user type (Farmer or Buyer)
    val uiState = viewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        // User Type Switch Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = viewModel::switchToFarmerReg,
                enabled = uiState.value.userType != "Farmer"
            ) {
                Text("Farmer Registration")
            }

            TextButton(
                onClick = viewModel::switchToBuyerReg,
                enabled = uiState.value.userType != "Buyer"
            ) {
                Text("Buyer Registration")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Register as ${uiState.value.userType}",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shared Input Fields
        TextField(
            value = uiState.value.name,
            onValueChange = viewModel::updateName,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.value.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.value.phoneNumber,
            onValueChange = viewModel::updatePhoneNumber,
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.value.userType == "Farmer") {
            // Farmer-Specific Input Fields
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.farmAddress,
                onValueChange = viewModel::updateFarmAddress,
                label = { Text("Farm Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.farmSize,
                onValueChange = viewModel::updateFarmSize,
                label = { Text("Farm Size") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.cropTypes,
                onValueChange = viewModel::updateCropTypes,
                label = { Text("Types of Crops") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.govtId,
                onValueChange = viewModel::updateGovtId,
                label = { Text("Government-issued ID") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (uiState.value.userType == "Buyer") {
            // Buyer-Specific Input Fields
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.deliveryAddress,
                onValueChange = viewModel::updateDeliveryAddress,
                label = { Text("Delivery Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.value.paymentMethod,
                onValueChange = viewModel::updatePaymentMethod,
                label = { Text("Preferred Payment Method") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                if (uiState.value.userType == "Farmer") viewModel.registerFarmer() else viewModel.registerBuyer()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display Error or Success Message
        if (uiState.value.errorMessage.isNotBlank()) {
            Text(uiState.value.errorMessage, color = MaterialTheme.colorScheme.error)
        }

        if (uiState.value.registrationStatus.isNotBlank()) {
            Text(uiState.value.registrationStatus, color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back to Login Button
        TextButton(onClick = onNavigateToLogin) {
            Text("Back to Login")
        }
    }
}


@Preview
@Composable
fun RegistrationScreenPreview(){
    RegistrationScreen(
        onNavigateToLogin = {},
    )
}