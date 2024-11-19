package com.example.farmerapp

import android.app.Application
import com.example.farmerapp.data.AppContainer
import com.example.farmerapp.data.DefaultAppContainer

class FarmerMarketApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}