package com.example.farmerapp.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    // State to track user type (Farmer or Buyer)
    var userType by remember { mutableStateOf("Farmer") }
    val uiState by viewModel.uiState.collectAsState()

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
                onClick = { userType = "Farmer" },
                enabled = userType != "Farmer"
            ) {
                Text("Farmer Registration")
            }

            TextButton(
                onClick = { userType = "Buyer" },
                enabled = userType != "Buyer"
            ) {
                Text("Buyer Registration")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Register as $userType",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shared Input Fields
        TextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.phoneNumber,
            onValueChange = viewModel::updatePhoneNumber,
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        if (userType == "Farmer") {
            // Farmer-Specific Input Fields
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.farmAddress,
                onValueChange = viewModel::updateFarmAddress,
                label = { Text("Farm Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.farmSize,
                onValueChange = viewModel::updateFarmSize,
                label = { Text("Farm Size") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.cropTypes,
                onValueChange = viewModel::updateCropTypes,
                label = { Text("Types of Crops") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.govtId,
                onValueChange = viewModel::updateGovtId,
                label = { Text("Government-issued ID") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (userType == "Buyer") {
            // Buyer-Specific Input Fields
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.deliveryAddress,
                onValueChange = viewModel::updateDeliveryAddress,
                label = { Text("Delivery Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = uiState.paymentMethod,
                onValueChange = viewModel::updatePaymentMethod,
                label = { Text("Preferred Payment Method") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                if (userType == "Farmer") viewModel.registerFarmer() else viewModel.registerBuyer()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display Error or Success Message
        if (uiState.errorMessage.isNotBlank()) {
            Text(uiState.errorMessage, color = MaterialTheme.colorScheme.error)
        }

        if (uiState.registrationStatus.isNotBlank()) {
            Text(uiState.registrationStatus, color = MaterialTheme.colorScheme.primary)
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