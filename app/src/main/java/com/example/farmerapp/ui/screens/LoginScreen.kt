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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToFarmerDashboard: () -> Unit,
    onNavigateToBuyerDashboard: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Logo

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            TextButton(
                onClick = { viewModel.updateRole(Role.Buyer) },
                enabled = uiState.value.chosenRole != Role.Buyer
            ) {
                Text("Buyer")
            }

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Bottom)
            )

            TextButton(
                onClick = { viewModel.updateRole(Role.Farmer) },
                enabled = uiState.value.chosenRole != Role.Farmer
            ) {
                Text("Farmer")
            }
        }


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
            onClick = {
                coroutineScope.launch {
                    viewModel.login()
                }

            }, // Use the navigation callback here
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (uiState.value.loginState is AuthorizationState.Loading) "Loading..." else if(uiState.value.loginState is AuthorizationState.Success) "Enter app" else "Login")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password and Register Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(onClick = onNavigateToRegister) {
                Text("Register", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
            }
        }

        when(uiState.value.loginState) {
            is AuthorizationState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthorizationState.Success -> {
                Text(
                    (uiState.value.loginState as AuthorizationState.Success).successMsg,
                    color = Color(0xFF24652A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                if (uiState.value.loginState is AuthorizationState.Success) {
                    viewModel.setLoginInitial()
                    viewModel.setLoginInitial()
                    if (uiState.value.chosenRole == Role.Farmer) {
                        onNavigateToFarmerDashboard()
                    } else {
                        onNavigateToBuyerDashboard()
                    }
                }
            }
            is AuthorizationState.Error -> {
                Text(
                    (uiState.value.loginState as AuthorizationState.Error).errorMsg,
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
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToRegister = {},
        onNavigateToBuyerDashboard = {},
        onNavigateToFarmerDashboard = {}
    )
}