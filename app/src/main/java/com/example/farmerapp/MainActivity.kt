package com.example.farmerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.farmerapp.ui.theme.FarmerAppTheme

import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.farmerapp.ui.navigation.AppNavigation
import com.example.farmerapp.ui.theme.FarmerAppTheme // Import the theme


import com.example.farmerapp.ui.screens.BuyerInterfaceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuyerInterfaceScreen()
        }
    }
}
