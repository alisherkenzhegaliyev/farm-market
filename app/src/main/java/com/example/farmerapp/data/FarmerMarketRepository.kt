package com.example.farmerapp.data

import android.util.Log
import com.example.farmerapp.model.AddUpdateRequest
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Id
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.Product
import com.example.farmerapp.model.RequestResponse
import com.example.farmerapp.model.RegistrationRequest
import com.example.farmerapp.service.FarmerMarketApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface FarmerMarketRepository {
    suspend fun login(email: String, password: String, role: String): Response<RequestResponse>
    suspend fun registerFarmer(
        name: String,
        email: String,
        password: String,
        phone: String,
        farmAddress: String,
        farmSize: String,
        cropTypes: String,
        govtId: String
    ): Response<RequestResponse>

    suspend fun registerBuyer(
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String,
        ppm: String
    ): Response<RequestResponse>

    suspend fun updateProduct(
        id: Int,
        name: String,
        price: Float,
        quantity: Int
    ): Response<RequestResponse>

    suspend fun getFarmersProduct(id: Int): Flow<List<Product>>

    suspend fun deleteProduct(id: Int): Response<RequestResponse>

    suspend fun addProduct(request: AddUpdateRequest): Response<RequestResponse>

    suspend fun getProduct(id: Int): Product

    fun getAllProducts(): Flow<List<Product>>

    suspend fun addToCart(cartItem: Cart): Response<RequestResponse>

    suspend fun getCartItems(buyerId: Int): List<Cart>
}

class DefaultFarmerMarketRepository(private val farmerMarketApiService: FarmerMarketApiService) : FarmerMarketRepository {
    override suspend fun login(email: String, password: String, role: String): Response<RequestResponse> {
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
    ): Response<RequestResponse> {
        val request = RegistrationRequest(
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
        return farmerMarketApiService.register(request)
    }

    override suspend fun registerBuyer(
        name: String,
        email: String,
        password: String,
        phone: String,
        address: String,
        ppm: String
    ): Response<RequestResponse> {
        val request = RegistrationRequest(
            name = name,
            email = email,
            password = password,
            userType = "Buyer",
            phoneNumber = phone,
            address = address,
            ppm = ppm
        )
        return farmerMarketApiService.register(request)
    }

    override suspend fun updateProduct(
        id: Int,
        name: String,
        price: Float,
        quantity: Int
    ): Response<RequestResponse> {
        val requestBody = AddUpdateRequest(id = id, name = name, price = price, quantity = quantity)
        return farmerMarketApiService.updateProduct(id, requestBody)
    }

    override suspend fun getFarmersProduct(id: Int): Flow<List<Product>> {
        return flow {
            emit(farmerMarketApiService.getFarmersProducts(id))
        }
    }

    override suspend fun deleteProduct(id: Int): Response<RequestResponse> {
        val requestBody = Id(id)
        return farmerMarketApiService.deleteProduct(requestBody)
    }

    override suspend fun addProduct(request: AddUpdateRequest): Response<RequestResponse> {
        return farmerMarketApiService.addProduct(request)
    }

    override suspend fun getProduct(id: Int): Product {
        return farmerMarketApiService.getProduct(id)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return flow {
            Log.i("getproducts", "in get products")
            emit(farmerMarketApiService.getAllProducts())
        }
    }

    override suspend fun addToCart(cartItem: Cart): Response<RequestResponse> {
        return farmerMarketApiService.addToCart(cartItem)
    }

    override suspend fun getCartItems(buyerId: Int): List<Cart> {
        return farmerMarketApiService.getCartItems(buyerId)
    }

}

