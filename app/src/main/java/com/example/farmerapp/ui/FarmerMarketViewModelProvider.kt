package com.example.farmerapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farmerapp.FarmerMarketApplication
import com.example.farmerapp.ui.farmer.AddProductViewModel
import com.example.farmerapp.ui.farmer.EditScreenViewModel
import com.example.farmerapp.ui.screens.LoginViewModel
import com.example.farmerapp.ui.screens.RegistrationViewModel


object FarmerMarketViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                farmerMarketApplication().container.farmerMarketRepository
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
                farmerMarketApplication().container.farmerMarketRepository
            )
        }
    }
}

fun CreationExtras.farmerMarketApplication() : FarmerMarketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FarmerMarketApplication)