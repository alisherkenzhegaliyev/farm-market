package com.example.farmerapp.service

import com.example.farmerapp.model.Product
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface FarmerMarketApiService {
    // Replace these with actual API calls
    @GET("products/{productId}")
    suspend fun getProductById(productId: Int): Product?
    @GET("products")
    suspend fun searchProducts(query: String): List<Product>
    @POST("products")
    suspend fun addProduct(product: Product)
    @PUT("products/{productId}")
    suspend fun updateProduct(product: Product)
    @DELETE("products/{productId}")
    suspend fun deleteProduct(productId: Int)
    @GET("products")
    suspend fun getProducts(): List<Product>

}
