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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(onBack: () -> Unit, viewModel: AddProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
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
                viewModel = viewModel
            )
        }
    )
}

@Composable
fun AddProductContent(modifier: Modifier = Modifier, viewModel: AddProductViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = viewModel.name.value,
            onValueChange = viewModel::updateName,
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.category.value,
            onValueChange = viewModel::updateCategory,
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.price.value,
            onValueChange = viewModel::updatePrice,
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.quantity.value,
            onValueChange = viewModel::updateQuantity,
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = viewModel.description.value,
            onValueChange = viewModel::updateDescription,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { viewModel.saveProduct() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Product")
        }
        if (viewModel.isSaved.value) {
            Text("Product saved successfully!", color = MaterialTheme.colorScheme.primary)
        }
    }
}
