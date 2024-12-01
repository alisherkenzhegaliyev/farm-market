package com.example.farmerapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farmerapp.FarmerMarketApplication
import com.example.farmerapp.ui.buyer.BuyerDashboardViewModel
import com.example.farmerapp.ui.buyer.ChatViewModel
import com.example.farmerapp.ui.buyer.ProfileViewModel
import com.example.farmerapp.ui.farmer.AddProductViewModel
import com.example.farmerapp.ui.farmer.EditScreenViewModel
import com.example.farmerapp.ui.farmer.ManageProductViewModel
import com.example.farmerapp.ui.navigation.AppNavigation
import com.example.farmerapp.ui.navigation.NavigationViewModel
import com.example.farmerapp.ui.screens.LoginViewModel
import com.example.farmerapp.ui.screens.RegistrationViewModel


object FarmerMarketViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                farmerMarketApplication().container.farmerMarketRepository,
                farmerMarketApplication().sessionManager
            )
        }

        initializer {
            RegistrationViewModel(
                farmerMarketApplication().container.farmerMarketRepository
            )
        }

        initializer {
            EditScreenViewModel(
                this.createSavedStateHandle(),
                farmerMarketApplication().container.farmerMarketRepository
            )
        }

        initializer {
            AddProductViewModel(
                farmerMarketApplication().container.farmerMarketRepository,
                sessionManager = farmerMarketApplication().sessionManager
            )
        }

        initializer {
            ManageProductViewModel(
                farmerMarketApplication().container.farmerMarketRepository,
                farmerMarketApplication().sessionManager
            )
        }

        initializer {
            NavigationViewModel(
                farmerMarketApplication().sessionManager
            )
        }

        initializer {
            ChatViewModel(
                farmerMarketApplication().container.farmerMarketRepository
            )
        }

        initializer {
            ProfileViewModel(
                farmerMarketApplication().sessionManager
            )
        }

        initializer {
            BuyerDashboardViewModel(
                farmerMarketApplication().container.farmerMarketRepository,
                farmerMarketApplication().sessionManager
            )
        }

    }
}


fun CreationExtras.farmerMarketApplication() : FarmerMarketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FarmerMarketApplication)
