package com.example.farmerapp.service

import com.example.farmerapp.model.AddUpdateRequest
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Id
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.RequestResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FarmerMarketApiService {
    @POST("/api/login/")
    suspend fun login(@Body request: LoginRequest): Response<RequestResponse>

    @POST("/api/register/")
    suspend fun register(@Body request: RegistrationRequest): Response<RequestResponse>

    @GET("/api/farmer/{id}/products/")
    suspend fun getFarmersProducts(@Path("id") id: Int): List<Product>

    @POST("/api/farmer/product/add/")
    suspend fun addProduct(@Body product: AddUpdateRequest): Response<RequestResponse>

    @POST("/api/deleteproduct/")
    suspend fun deleteProduct(@Body id: Id): Response<RequestResponse>

    @POST("/api/product/{id}/update/")
    suspend fun updateProduct(@Path("id") id: Int, @Body updateRequest: AddUpdateRequest): Response<RequestResponse>

    @GET("/api/product/{id}/")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("/api/products/")
    fun getProducts(): Flow<List<Product>>

    @POST("/api/product/add/cart")
    suspend fun addToCart(@Body cartItem: Cart): Response<RequestResponse>
}


