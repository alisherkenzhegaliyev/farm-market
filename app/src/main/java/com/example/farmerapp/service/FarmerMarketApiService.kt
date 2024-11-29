package com.example.farmerapp.service

import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.RequestResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.model.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FarmerMarketApiService {
    @POST("/api/login/")
    suspend fun login(@Body request: LoginRequest): Response<RequestResponse>

    @POST("/api/register/")
    suspend fun register(@Body request: RegistrationRequest): Response<RequestResponse>

    @GET("/api/farmer/{id}/products/")
    suspend fun getFarmersProducts(@Path("id") id: String): List<Product>

    @POST("/api/farmer/addproduct/")
    suspend fun addProduct(@Body product: Product): Response<RequestResponse>

    @DELETE("/api/farmer/deleteproduct/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<RequestResponse>

    @POST("/api/farmer/updateproduct/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product): Response<RequestResponse>
}
