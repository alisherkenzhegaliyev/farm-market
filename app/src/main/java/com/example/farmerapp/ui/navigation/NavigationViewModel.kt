package com.example.farmerapp.ui.navigation

import androidx.lifecycle.ViewModel
import com.example.farmerapp.data.preferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _currentRoute = MutableStateFlow(Screens.login)
    val currentRoute: MutableStateFlow<Screens> = _currentRoute

    init {
        defineInitialRoute()
    }

    private fun defineInitialRoute() {
        val initialRoute = if (sessionManager.isActive()) {
            if(sessionManager.getUserType() == "Farmer") Screens.farmer_dashboard
            else Screens.buyer_interface
        } else {
            Screens.login
        }
    }

}

enum class Screens {
    login,
    farmer_dashboard,
    buyer_interface
}

