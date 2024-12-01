import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmerapp.ui.buyer.CartScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: CartScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val cartItems = viewModel.cartItems
    val totalPrice = viewModel.getTotalPrice()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Cart") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
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
            Text(text = cartItem.name, fontSize = 16.sp)
            Text(text = "Price: $${"%.2f".format(cartItem.price)}", fontSize = 14.sp)
        }
        Text(text = "x${cartItem.quantity}")
    }
}

data class CartItem(val name: String, val price: Double, val quantity: Int)

@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    CartScreen(viewModel = CartScreenViewModel())
}