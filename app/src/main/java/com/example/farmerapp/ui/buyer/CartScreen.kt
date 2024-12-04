import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.model.Cart
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import com.example.farmerapp.ui.buyer.BuyerState
import com.example.farmerapp.ui.buyer.CartScreenViewModel
import com.example.farmerapp.ui.farmer.ManageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartScreenViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
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
                if(uiState.state is BuyerState.Success) {
                    Toast.makeText(context, (uiState.state as BuyerState.Success).message, Toast.LENGTH_SHORT).show()
                }
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems.size) { index ->
                        CartItemRow(cartItem = cartItems[index], viewModel::removeItemFromCart)
                        HorizontalDivider()
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
                    onClick = viewModel::checkout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Checkout")
                }
            }
        }
    )
}

@Composable
fun CartItemRow(cartItem: Cart, deleteItemFromCart: (Cart) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = cartItem.productname, fontSize = 16.sp)
            Text(text = "Price: $${"%.2f".format(cartItem.productprice)}", fontSize = 14.sp)
            IconButton(onClick = {
                deleteItemFromCart(cartItem)
            }) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Remove Item",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        Text(text = "x${cartItem.quantity}")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    CartScreen(onBack = {})
}