package com.example.farmerapp.ui.buyer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.model.Order
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreenUI(
    viewModel: OrderViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onBackClick: () -> Unit
) {


    val orderList by viewModel.orders.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        IconButton(onClick = onBackClick) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        Text("Orders")
                        IconButton(onClick = viewModel::getOrders) {
                            Icon(imageVector = Icons.Default.Replay, contentDescription = "Update orders list")
                        }
                    }
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary))
        }
    ) { padding ->
        if(!orderList.isLoading) {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(orderList.orders) { order ->
                    OrderCard(order, userType = viewModel.userType!!, onUpdateStatus = viewModel::updateOrder)
                }
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun OrderCard(order: Order, userType: String, onUpdateStatus: (Order,  Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Order Details Section
            Text(
                text = "Order Date: ${order.orderdate ?: "N/A"}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Product Name: ${order.productname}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)

            // Buyer and Order Details
            Text(
                text = "Order ID: ${order.orderid ?: "N/A"}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Buyer ID: ${order.buyerid}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)

            // Order Summary Section
            Text(
                text = "Total Amount: ${order.totalamount}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Total Price: ${order.totalprice}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(8.dp))

            // Status Section with Colored Badge
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Status: ${order.orderstatus}",
                    color = when (order.orderstatus) {
                        "Pending" -> Color.Yellow
                        "Accepted" -> Color.Green
                        "Rejected" -> Color.Red
                        else -> Color.Gray
                    },
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .background(
                            color = when (order.orderstatus) {
                                "Pending" -> Color.Yellow.copy(alpha = 0.2f)
                                "Accepted" -> Color.Green.copy(alpha = 0.2f)
                                "Rejected" -> Color.Red.copy(alpha = 0.2f)
                                else -> Color.Gray.copy(alpha = 0.2f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(36.dp)
                        .wrapContentWidth()
                )

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {

                    },
                    Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(36.dp)
                        .wrapContentWidth()
                ) {
                    Text("Delete order")
                }
            }
            Spacer(Modifier.height(8.dp))

            // Actions for Farmers
            if (userType == "Farmer" && order.orderstatus == "Pending") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            onUpdateStatus(order, false)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbDown,
                            contentDescription = "Reject Order",
                            tint = Color.Red
                        )
                    }
                    IconButton(
                        onClick = {
                            onUpdateStatus(order, true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Accept Order",
                            tint = Color.Green
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderCardPreview() {
    val order = Order(
        orderid = 1,
        buyerid = 1,
        orderdate = "2023-04-01",
        productname = "Apple",
        totalamount = 10,
        totalprice = 100.0f,
        orderstatus = "Pending",
        farmerid = 15
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { OrderCard(order, "Farmer", onUpdateStatus = {_, _ -> }) }
}

