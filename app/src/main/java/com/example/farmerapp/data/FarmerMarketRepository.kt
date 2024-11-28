package com.example.farmerapp.data

import android.util.Log
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.LoginResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.service.FarmerMarketApiService
import retrofit2.Response
import retrofit2.http.Body

interface FarmerMarketRepository {
    suspend fun login(email: String, password: String, role: String): Response<LoginResponse>
}

class DefaultFarmerMarketRepository(private val farmerMarketApiService: FarmerMarketApiService) : FarmerMarketRepository {
    override suspend fun login(email: String, password: String, role: String): Response<LoginResponse> {
        Log.i("LoginViewModel", "login() called with email: $email, password: $password, role: $role")
        val request = LoginRequest(email, password, role)
        Log.i("LoginViewModel", "LoginRequest created: $request")
        return farmerMarketApiService.login(request)
    }
}
