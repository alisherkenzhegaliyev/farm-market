package com.example.farmerapp.data

import android.util.Log
import com.example.farmerapp.model.AddUpdateRequest
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Chat
import com.example.farmerapp.model.Id
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.Message
import com.example.farmerapp.model.Order
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

    suspend fun getUserChats(userId: Int, userType: String): List<Chat>

    suspend fun getChatMessages(chatId: Int): List<Message>

    suspend fun getFarmerName(id: Int): RequestResponse

    suspend fun getBuyerName(id: Int): RequestResponse

    suspend fun sendMessage(message: Message): Response<RequestResponse>

    suspend fun sendChat(chat: Chat): Response<RequestResponse>

    suspend fun deleteCartItem(cartId: Int): Response<RequestResponse>

    suspend fun addOrder(order: Order): Response<RequestResponse>

    suspend fun getOrders(id: Int, userType: String): List<Order>

    suspend fun deleteOrder(id: Int): Response<RequestResponse>

    suspend fun updateOrder(order: Order): Response<RequestResponse>


}

//--------------------------------------------------------------------
//-------------------REPOSITORY IMPLEMENTATION------------------------
//--------------------------------------------------------------------
//--------------------------------------------------------------------

class DefaultFarmerMarketRepository(private val farmerMarketApiService: FarmerMarketApiService) : FarmerMarketRepository {
    override suspend fun login(email: String, password: String, role: String): Response<RequestResponse> {
        Log.i("LoginViewModel", "login() called with email: $email, password: $password, role: $role")
        val request = LoginRequest(email, password, role)
        Log.i("LoginViewModel", "LoginRequest created: $request")
        return farmerMarketApiService.login(request)

    }

    override suspend fun deleteCartItem(cartId: Int): Response<RequestResponse> {
        return farmerMarketApiService.deleteCartItem(cartId)
    }

    override suspend fun addOrder(order: Order): Response<RequestResponse> {
        return farmerMarketApiService.addOrder(order)
    }

    override suspend fun deleteOrder(id: Int): Response<RequestResponse> {
        return farmerMarketApiService.deleteOrder(id)
    }

    override suspend fun updateOrder(order: Order): Response<RequestResponse> {
        return farmerMarketApiService.updateOrder(order)
    }

    override suspend fun getOrders(id: Int, userType: String): List<Order> {
        return farmerMarketApiService.getOrders(id, userType)
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
            while(true) {
                Log.i("getproducts", "in get products")
                emit(farmerMarketApiService.getAllProducts())
                delay(5000L)
            }
        }
    }

    override suspend fun addToCart(cartItem: Cart): Response<RequestResponse> {
        Log.i("addToCart", "in add to cart with cartItem: $cartItem")
        return farmerMarketApiService.addToCart(cartItem)
    }

    override suspend fun getCartItems(buyerId: Int): List<Cart> {
        return farmerMarketApiService.getCartItems(buyerId)
    }

    override suspend fun getUserChats(userId: Int, userType: String): List<Chat> {
        return farmerMarketApiService.getUserChats(userId, userType)
    }

    override suspend fun getChatMessages(chatId: Int): List<Message> {
        return (farmerMarketApiService.getChatMessages(chatId))
    }

    override suspend fun sendMessage(message: Message): Response<RequestResponse> {
        return farmerMarketApiService.sendMessage(message)
    }

    override suspend fun sendChat(chat: Chat): Response<RequestResponse> {
        return farmerMarketApiService.sendChat(chat)
    }

    override suspend fun getFarmerName(id: Int): RequestResponse {
        return farmerMarketApiService.getFarmerName(id)
    }

    override suspend fun getBuyerName(id: Int): RequestResponse {
        return farmerMarketApiService.getBuyerName(id)
    }




}

