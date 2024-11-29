package com.example.farmerapp.ui.buyer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        fetchMessages() // Simulate fetching data
    }

    fun fetchMessages() {
        viewModelScope.launch {
            delay(1000) // Simulate network delay
            _messages.value = listOf(
                Message(senderId = "Farmer", content = "Hello, do you want to buy some apples?", timestamp = System.currentTimeMillis()),
                Message(senderId = "Buyer", content = "Yes, how much are they?", timestamp = System.currentTimeMillis())
            )
        }
    }

    fun sendMessage(message: Message) {
        val updatedMessages = _messages.value.toMutableList()
        updatedMessages.add(message)
        _messages.value = updatedMessages
    }
}
