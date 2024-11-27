package com.example.farmerapp.data

import com.example.farmerapp.model.Product
import com.example.farmerapp.service.FarmerMarketApiService

interface FarmerMarketRepository {

//    suspend fun getProductById(productId: Int): Product?
//    suspend fun searchProducts(query: String): List<Product>
//    suspend fun addProduct(product: Product)
//    suspend fun updateProduct(product: Product)
//    suspend fun deleteProduct(productId: Int)
//    suspend fun getProducts(): List<Product>
}

class DefaultFarmerMarketRepository(private val farmerMarketApiService: FarmerMarketApiService) : FarmerMarketRepository {

//    override suspend fun getProductById(productId: Int): Product? {
//        return farmerMarketApiService.getProductById(productId)
//    }
//
//    override suspend fun searchProducts(query: String): List<Product> {
//        return farmerMarketApiService.searchProducts(query)
//    }
//
//    override suspend fun addProduct(product: Product) {
//        farmerMarketApiService.addProduct(product)
//    }
//
//    override suspend fun updateProduct(product: Product) {
//        farmerMarketApiService.updateProduct(product)
//    }
//
//    override suspend fun deleteProduct(productId: Int) {
//        farmerMarketApiService.deleteProduct(productId)
//    }

//    override suspend fun getProducts(): List<Product> {
//        return farmerMarketApiService.getProducts()
//    }
}
