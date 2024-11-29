package com.example.farmerapp

import android.app.Application
import com.example.farmerapp.data.AppContainer
import com.example.farmerapp.data.DefaultAppContainer
import com.example.farmerapp.data.preferences.SessionManager

class FarmerMarketApplication : Application() {
    lateinit var container: AppContainer
    lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()

        sessionManager = SessionManager(this)
    }
}