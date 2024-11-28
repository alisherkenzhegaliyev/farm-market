package com.example.farmerapp.service

import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.LoginResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.model.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface FarmerMarketApiService {
    @POST("/api/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/register/")
    suspend fun register(@Body request: RegistrationRequest): Response<LoginResponse>


}
