package com.example.farmerapp.ui.farmer

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProductsScreen(
    onBack: () -> Unit,
    onEditScreen: (Int  ) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var isSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Products", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                ManageProductsContent(
                    modifier = Modifier.padding(padding),
                    onEditScreen = onEditScreen,
                    onProductClick = {
                        coroutineScope.launch {
                            isSheetVisible = true
                            sheetState.show()
                        }
                    }
                )
            }

            // Modal Bottom Sheet for animation
            if (isSheetVisible) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        coroutineScope.launch {
                            sheetState.hide()
                            isSheetVisible = false
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            // Add description logic here Islam
                            text = "Product Selected",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ManageProductsContent(
    modifier: Modifier = Modifier,
    onEditScreen: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: ManageProductViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory)
) {
    // Dynamic Product List

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val products = uiState.productList

    if(uiState.manageState is ManageState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {
        if(uiState.manageState is ManageState.Success) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Toast.makeText(context, (uiState.manageState as ManageState.Success).successMsg, Toast.LENGTH_SHORT).show()
            }
        }

        if (products.isEmpty() && uiState.manageState is ManageState.Success) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This user doesn't have any products")
            }

        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(products.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Product Name
                    Text(
                        text = products[index].name,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { onProductClick(index) }
                    )

                    // Action Buttons (Edit and Delete)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ClickableText(
                            text = AnnotatedString("Edit"),
                            onClick = { onEditScreen(products[index].productID) }
                        )

                        IconButton(onClick = {
                            viewModel.deleteProduct(products[index].productID)

                        }) {
                            Icon(
                                Icons.Filled.DeleteOutline,
                                contentDescription = "Delete",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }



}
