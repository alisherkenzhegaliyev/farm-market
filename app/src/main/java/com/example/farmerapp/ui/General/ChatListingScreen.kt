package com.example.farmerapp.ui.General

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.model.Chat
import com.example.farmerapp.ui.FarmerMarketViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    viewModel: ChatListingViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory), // ViewModel for data
    onChatSelected: (Int) -> Unit, // Callback for when a chat is clicked
    onBack: () -> Unit // Back button callback
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            when {
                uiState.requestState is RequestState.Error -> {
                    // Show Error Message
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = (uiState.requestState as RequestState.Error).errorMsg)
                    }
                }

                uiState.requestState is RequestState.Loading -> {
                    // Show Loading Spinner
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                uiState.chatList.isEmpty() -> {
                    // Show Empty State
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Chats Available")
                    }
                }

                else -> {
                    // Show Chat List
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.chatList) { chat ->
                            Log.i("ChatListScreen", "Chat: ${chat.chat.chatid}")
                            ChatItem(chat = chat, onClick = { onChatSelected(chat.chat.chatid!!) })
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ChatItem(chat: ChatPreview, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Chat with: ${chat.fromName}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
