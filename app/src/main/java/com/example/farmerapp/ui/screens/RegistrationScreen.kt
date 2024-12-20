package com.example.farmerapp.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onNavigateToLogin: () -> Unit
) {
    // State to track user type (Farmer or Buyer)
    val uiState = viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

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
            value = uiState.value.password,
            onValueChange = viewModel::updatePassword,
            label = { Text("Password") },
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
                value = uiState.value.govtId,
                onValueChange = viewModel::updateGovtId,
                label = { Text("Government-issued ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = uiState.value.cropTypes.ifBlank { "Select Crop Type"})
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val farmOptions = listOf("Vegetables", "Fruits", "Grains", "Dairy")
                    farmOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updateCropTypes(option)
                                expanded = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
            }


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

            var expanded by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = uiState.value.paymentMethod.ifBlank { "Select Payment Method" })
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val paymentOptions = listOf("apple pay", "google pay", "card")
                    paymentOptions.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.updatePaymentMethod(option)
                                expanded = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                coroutineScope.launch{ viewModel.register() }
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

        when(uiState.value.registrationState) {
            is AuthorizationState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthorizationState.Success -> {
                Text(
                    (uiState.value.registrationState as AuthorizationState.Success).successMsg,
                    color = Color.Green,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                viewModel.setInitialUi()
            }
            is AuthorizationState.Error -> {
                Text(
                    (uiState.value.registrationState as AuthorizationState.Error).errorMsg,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            else -> {}
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