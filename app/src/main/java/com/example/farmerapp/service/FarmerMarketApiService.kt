package com.example.farmerapp.service

import com.example.farmerapp.model.BuyerRegistrationRequest
import com.example.farmerapp.model.FarmerRegistrationRequest
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.LoginResponse
import com.example.farmerapp.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface FarmerMarketApiService {
    @POST("/api/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/register/farmer/")
    suspend fun registerFarmer(@Body request: FarmerRegistrationRequest): Response<LoginResponse>

    @POST("/api/register/buyer/")
    suspend fun registerBuyer(@Body request: BuyerRegistrationRequest): Response<LoginResponse>



}
