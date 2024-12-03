package com.example.farmerapp.service

import com.example.farmerapp.model.AddUpdateRequest
import com.example.farmerapp.model.Cart
import com.example.farmerapp.model.Chat
import com.example.farmerapp.model.Id
import com.example.farmerapp.model.LoginRequest
import com.example.farmerapp.model.Message
import com.example.farmerapp.model.RequestResponse
import com.example.farmerapp.model.Product
import com.example.farmerapp.model.RegistrationRequest
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
    suspend fun getAllProducts(): List<Product>

    @POST("/api/product/add/cart/")
    suspend fun addToCart(@Body cartItem: Cart): Response<RequestResponse>

    @GET("/api/product/cart/{buyerid}/")
    suspend fun getCartItems(@Path("buyerid") buyerId: Int): List<Cart>

    @GET("/api/buyer/chats/{userId}/{usertype}/")
    suspend fun getUserChats(@Path("userId") userId: Int, @Path("usertype") userType: String): List<Chat>

    @POST("/api/send/message/")
    suspend fun sendMessage(@Body message: Message): Response<RequestResponse>

    @POST("/api/send/chat/")
    suspend fun sendChat(@Body chat: Chat): Response<RequestResponse>

    @GET("/api/chat/{chatId}/messages/")
    suspend fun getChatMessages(@Path("chatId") chatId: Int): List<Message>

    @GET("/api/farmer/name/{id}/")
    suspend fun getFarmerName(@Path("id") id: Int): RequestResponse

    @GET("/api/buyer/name/{id}/")
    suspend fun getBuyerName(@Path("id") id: Int): RequestResponse

}


