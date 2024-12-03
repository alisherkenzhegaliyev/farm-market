package com.example.farmerapp.ui.General

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    savedStateHandle: SavedStateHandle,
    sessionManager: SessionManager
) : ViewModel() {
    private val _messages = MutableStateFlow(ChatUiState())
    val messages = _messages

    val chatId = savedStateHandle.get<Int>("id") ?: -1
    val senderType = sessionManager.getUserType()


    init {
        Log.i("ChatViewModel_init", "chatId is $chatId")
        fetchMessages() // Simulate fetching data
    }

    fun fetchMessages() {
        Log.i("ChatViewModel", "In fetch messages ")
        viewModelScope.launch {
            _messages.update { it.copy(requestState = RequestState.Loading) }
            if(chatId != -1) {
                Log.i("ChatViewModel", "chatId is $chatId")
                try {
                    val messages = farmerMarketRepository.getChatMessages(chatId)
                    if(messages.isEmpty()) {
                         _messages.update {
                             it.copy(
                                 messages = messages,
                                 requestState = RequestState.Success("Empty, send some message")
                             )
                         }
                    }
                    else {
                        _messages.update {
                            it.copy(
                                messages = messages,
                                requestState = RequestState.Success("Success")
                            )
                        }
                    }

                } catch (e: Exception) {
                    _messages.update {
                        it.copy(requestState = RequestState.Error(e.message ?: "Unknown error"))
                    }
                }
            } else {
                _messages.update {
                    it.copy(requestState = RequestState.Error("Invalid chatId"))
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                farmerMarketRepository.sendMessage(Message(
                    chatid = chatId,
                    sendertype = senderType!!,
                    messagetext = message,
                    sentat = System.currentTimeMillis().toString()
                ))
                _messages.update { it.copy(requestState = RequestState.Success("Message sent")) }
                fetchMessages()
            } catch(e: Exception) {
                _messages.update {
                    it.copy(requestState = RequestState.Error(e.message ?: "Unknown error"))
                }
            }
        }
    }

    fun updateEntry(entry: String) {
        _messages.update { it.copy(currentEntry = entry) }
    }
}

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val requestState: RequestState = RequestState.Idle,
    val currentEntry: String = ""
)

