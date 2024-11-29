package com.example.farmerapp.ui.buyer

data class Message(
    val senderId: String,
    val content: String,
    val timestamp: Long
)

data class ChatRoom(
    val id: String,
    val participants: List<String>,
    val messages: List<Message>
)
