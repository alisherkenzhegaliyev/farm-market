package com.example.farmerapp.data

import android.util.Log
import com.example.farmerapp.model.BuyerRegistrationRequest
import com.example.farmerapp.model.FarmerRegistrationRequest
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.LoginResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.service.FarmerMarketApiService
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.http.Body

interface FarmerMarketRepository {
    suspend fun login(email: String, password: String, role: String): Response<LoginResponse>
    suspend fun registerFarmer(
        name: String,
        email: String,
        password: String,
        phone: String,
        farmAddress: String,
        farmSize: String,
        cropTypes: String,
        govtId: String
    ): Response<LoginResponse>

    suspend fun registerBuyer(
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String,
        ppm: String
    ): Response<LoginResponse>


}

class DefaultFarmerMarketRepository(private val farmerMarketApiService: FarmerMarketApiService) : FarmerMarketRepository {
    override suspend fun login(email: String, password: String, role: String): Response<LoginResponse> {
        Log.i("LoginViewModel", "login() called with email: $email, password: $password, role: $role")
        val request = LoginRequest(email, password, role)
        Log.i("LoginViewModel", "LoginRequest created: $request")
        return farmerMarketApiService.login(request)

    }

    override suspend fun registerFarmer(
        name: String,
        email: String,
        password: String,
        phone: String,
        farmAddress: String,
        farmSize: String,
        cropTypes: String,
        govtId: String
    ): Response<LoginResponse> {
        val request = FarmerRegistrationRequest(
            name = name,
            email = email,
            password = password,
            userType = "Farmer",
            phoneNumber = phone,
            farmAddress = farmAddress,
            farmSize = farmSize,
            cropTypes = cropTypes,
            govtId = govtId
        )
        return farmerMarketApiService.registerFarmer(request)
    }

    override suspend fun registerBuyer(
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String,
        ppm: String
    ): Response<LoginResponse> {
        val request = BuyerRegistrationRequest(
            name = name,
            email = email,
            password = password,
            userType = "Buyer",
            phoneNumber = phone,
            address = address,
            ppm = ppm
        )
        return farmerMarketApiService.registerBuyer(request)
    }
}
