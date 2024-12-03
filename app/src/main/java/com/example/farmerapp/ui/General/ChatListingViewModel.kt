package com.example.farmerapp.ui.General

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmerapp.data.FarmerMarketRepository
import com.example.farmerapp.data.preferences.SessionManager
import com.example.farmerapp.model.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChatListingViewModel(
    private val farmerMarketRepository: FarmerMarketRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState = _uiState
    val userType = sessionManager.getUserType()
    val userId = sessionManager.getUserId()

    init {
        getChatList()
    }

    fun getChatList() {
        Log.i("ChatListingViewModel", "$userId $userType ")
        _uiState.value = _uiState.value.copy(requestState = RequestState.Loading)

        viewModelScope.launch {
            try {
                Log.i("ChatListingViewModel", "${userId.toInt()} | ${userType!!}")
                val response = farmerMarketRepository.getUserChats(userId.toInt(), userType!!)
                _uiState.value= _uiState.value.copy(
                    chatList = response.map {
                        val fromName = if(sessionManager.getUserType() == "Farmer") farmerMarketRepository.getBuyerName(it.buyerid) else farmerMarketRepository.getFarmerName(it.farmerid)
                        ChatPreview(it, fromName.name!!)
                    },
                    requestState = RequestState.Success("Success")
                )

                if(response.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        requestState = RequestState.Error("No chats available")
                    )
                }
            } catch(e : Exception) {
                _uiState.value = _uiState.value.copy(
                    requestState = RequestState.Error(e.message ?: "Couldn't retrieve chat list due exception")
                )
            }
        }
    }



}

data class ChatListUiState(
    val chatList: List<ChatPreview> = emptyList(),
    val requestState: RequestState = RequestState.Loading
)

sealed class RequestState {
    object Idle : RequestState()
    object Loading : RequestState()
    data class Success(val successMsg: String) : RequestState()
    data class Error(val errorMsg: String) : RequestState()
}

data class ChatPreview(
    val chat: Chat,
    val fromName: String,
)
