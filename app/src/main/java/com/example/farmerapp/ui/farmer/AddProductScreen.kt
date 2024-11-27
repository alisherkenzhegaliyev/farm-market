package com.example.farmerapp.ui.farmer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onBack: () -> Unit,
    viewModel: AddProductViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            AddProductContent(
                modifier = Modifier.padding(padding),
                uiState = uiState,
                onNameChange = viewModel::updateName,
                onCategoryChange = viewModel::updateCategory,
                onPriceChange = viewModel::updatePrice,
                onQuantityChange = viewModel::updateQuantity,
                onDescriptionChange = viewModel::updateDescription
            )
        }
    )
}

@Composable
fun AddProductContent(
    modifier: Modifier = Modifier,
    uiState: AddProductUiState,
    onNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.category,
            onValueChange = onCategoryChange,
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.price,
            onValueChange = onPriceChange,
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.quantity,
            onValueChange = onQuantityChange,
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Product")
        }
        if (uiState.isSaved) {
            Text("Product saved successfully!", color = MaterialTheme.colorScheme.primary)
        }
    }
}
