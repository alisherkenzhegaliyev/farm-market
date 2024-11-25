package com.example.farmerapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.farmerapp.FarmerMarketApplication
import com.example.farmerapp.ui.screens.LoginViewModel

object FarmerMarketViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                farmerMarketApplicatoin().container.farmerMarketRepository
            )
        }
    }
}

fun CreationExtras.farmerMarketApplicatoin() : FarmerMarketApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FarmerMarketApplication)