package com.example.farmerapp.ui.farmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Product ", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            EditProductContent(modifier = Modifier.padding(padding))
        }
    )


}


@Composable
fun EditProductContent(
    modifier: Modifier = Modifier,
    viewmodel: EditScreenViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {
    val uiState = viewmodel.uiState.collectAsState().value

    when (uiState.updateState) {
        is ManageState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            EditProductBody(
                uiState = uiState,
                onUpdateProduct = { viewmodel.updateProduct() },
            )
        }
    }
}


@Composable
fun EditProductBody(
    uiState: EditItemUIState,
    onUpdateProduct: () -> Unit,
    viewmodel: EditScreenViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var successMsg: String? = null
        var errorMsg: String? = null
        if(uiState.updateState is ManageState.Success){
            successMsg = (uiState.updateState as ManageState.Success).successMsg
        }
        if(uiState.updateState is ManageState.Error){
            errorMsg = (uiState.updateState as ManageState.Error).errorMsg
        }
        // Display Success or Error Message
        if(successMsg != null) {
            Text(
                text = successMsg,
                color = Color(0xFF196926),
                modifier = Modifier.align(Alignment.Start)
            )
        }
        if(errorMsg != null) {
            Text(
                text = errorMsg,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        // Editable Fields
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.name,
            onValueChange = viewmodel::updateNameField,
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.price,
            onValueChange = viewmodel::updatePriceField,
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.quantity,
            onValueChange = viewmodel::updateQuantityField,
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onUpdateProduct,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Product")
        }
    }
}


@Preview
@Composable
fun EditProductScreenPreview() {
    EditProductScreen(onBack = {})
}
//