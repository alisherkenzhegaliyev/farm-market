package com.example.farmerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.farmerapp.ui.theme.FarmerAppTheme

import com.example.farmerapp.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmerAppTheme { // Use the app theme for consistent styling
                AppNavigation() // Navigate through the app's routes
            }
        }
    }
}

