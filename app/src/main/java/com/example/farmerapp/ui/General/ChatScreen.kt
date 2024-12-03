package com.example.farmerapp.ui.General

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.model.Message
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    val userType = viewModel.senderType!!.lowercase()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Messages List
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(messages.messages) { message ->
                ChatMessageItem(message = message, userType = userType!!)
            }
        }

        // Message Input Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(32.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = messages.currentEntry,
                onValueChange = viewModel::updateEntry,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (messages.currentEntry.isEmpty()) {
                            Text("Type a message...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            IconButton(
                onClick = {
                    if (messages.currentEntry.isNotEmpty()) {
                        viewModel.sendMessage(
                            messages.currentEntry
                        )
                    viewModel.updateEntry("")// Clear the input
                    }
                }
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: Message, userType: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.sendertype == userType) Arrangement.End else Arrangement.Start
    ) {
        Log.i("ChatMessageItem", "Message: ${message.sendertype}, $userType")
        Box(
            modifier = Modifier
                .background(
                    if (message.sendertype == userType) Color(0xff5068F2) else Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.messagetext,
                color = if (message.sendertype == userType) Color.White else Color.Black
            )
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(onBack = {})
}