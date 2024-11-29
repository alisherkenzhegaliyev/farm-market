package com.example.farmerapp.ui.buyer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Profile(
    val name: String,
    val username: String,
    val profilePictureUrl: String,
    val language: String,
    val appVersion: String
)

class ProfileViewModel : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    init {
        // Simulate fetching user data
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            delay(2000) // Simulate network delay
            _profile.value = Profile(
                name = "Francisco Lachowski",
                username = "@chillyguy",
                profilePictureUrl = "https://preview.redd.it/chico-lachowski-damn-this-dude-is-handsome-personified-def-v0-rbyo3vm7rx0d1.jpg?width=385&format=pjpg&auto=webp&s=aec093236333846d396b5dd527af6ccec1da8cc4",
                language = "English",
                appVersion = "v1.0 -beta"
            )
        }
    }
}