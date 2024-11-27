package com.example.farmerapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farmerapp.FarmerMarketApplication
import com.example.farmerapp.ui.farmer.EditScreenViewModel
import com.example.farmerapp.ui.farmer.FarmerDashboardViewModel
import com.example.farmerapp.ui.screens.LoginViewModel
import com.example.farmerapp.ui.screens.RegistrationViewModel


object FarmerMarketViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                farmerMarketApplicatoin().container.farmerMarketRepository
            )
        }

        initializer {
            RegistrationViewModel(
                farmerMarketApplicatoin().container.farmerMarketRepository
            )
        }

        initializer {
            EditScreenViewModel(
                this.createSavedStateHandle(),
                farmerMarketApplicatoin().container.farmerMarketRepository
            )
        }

        initializer {
            FarmerDashboardViewModel(
                farmerMarketApplicatoin().container.farmerMarketRepository
            )
        }
    }
}

fun CreationExtras.farmerMarketApplicatoin() : FarmerMarketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FarmerMarketApplication)