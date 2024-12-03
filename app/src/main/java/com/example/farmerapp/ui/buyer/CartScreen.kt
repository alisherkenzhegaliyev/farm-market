import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import com.example.farmerapp.ui.buyer.BuyerState
import com.example.farmerapp.ui.buyer.CartItem
import com.example.farmerapp.ui.buyer.CartScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartScreenViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val cartItems = uiState.cartItems
    val totalPrice = uiState.totalPrice

    Log.i("CartScreen", "CartScreen with state ${uiState.state}")


    Scaffold(
        topBar = {
            TopAppBar(title = { Row() {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text("Your Cart")
            } })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if(uiState.state is BuyerState.Loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems.size) { index ->
                        CartItemRow(cartItem = cartItems[index])
                        Divider()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total: $${"%.2f".format(totalPrice)}", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Navigate to checkout */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Checkout")
                }
            }
        }
    )
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = cartItem.pr.name, fontSize = 16.sp)
            Text(text = "Price: $${"%.2f".format(cartItem.price)}", fontSize = 14.sp)
        }
        Text(text = "x${cartItem.quantity}")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    CartScreen(onBack = {})
}