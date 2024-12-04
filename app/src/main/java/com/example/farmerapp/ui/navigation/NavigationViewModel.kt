package com.example.farmerapp.ui.navigation

import androidx.lifecycle.ViewModel
import com.example.farmerapp.data.preferences.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _currentRoute = MutableStateFlow(NavigationUiState())
    val currentRoute = _currentRoute
    val currentUserType = sessionManager.getUserType()
    init {
        defineInitialRoute()
    }

    private fun defineInitialRoute() {
        val initialRoute = if (sessionManager.isActive()) {
            if(sessionManager.getUserType() == "Farmer") Screens.farmer_dashboard
            else Screens.buyer_home
        } else {
            Screens.login
        }
        _currentRoute.value = _currentRoute.value.copy(currentRoute = initialRoute)
    }

}
data class NavigationUiState(
    val currentRoute: Screens = Screens.login
)
enum class Screens {
    buyer_home,
    farmer_dashboard,
    login,
}

