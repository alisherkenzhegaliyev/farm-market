package com.example.farmerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToFarmerDashboard: () -> Unit,
    onNavigateToBuyerDashboard: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {

    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Logo
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "App Logo",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input Field
        TextField(
            value = uiState.value.emailEntry,
            onValueChange = { viewModel.updateEmailField(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input Field
        TextField(
            value = uiState.value.passwordEntry,
            onValueChange = { viewModel.updatePasswordField(it) },
            label = { Text("Password") },
            visualTransformation = if(uiState.value.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (uiState.value.passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                IconButton(onClick = { viewModel.updateVisibility() }) {
                    Icon(imageVector = image, contentDescription = null)
                }

            },
            modifier = Modifier.fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = onNavigateToFarmerDashboard, // Use the navigation callback here
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password and Register Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { /* Handle Forgot Password */ }) {
                Text("Forgot Password?")
            }
            TextButton(onClick = onNavigateToRegister) {
                Text("Register")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToRegister = {},
        onNavigateToBuyerDashboard = {},
        onNavigateToFarmerDashboard = {}
    )
}