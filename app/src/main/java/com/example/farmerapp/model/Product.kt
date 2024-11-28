package com.example.farmerapp.model

import com.google.gson.annotations.SerializedName


data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val farm: String,
    val description: String,
    val category: String,
    val rating: Int,
    val createdAt: Long
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_type") val userType: String // Make sure these match your API
)

data class LoginResponse(
    @SerializedName("message") val message: String?, // Example field, adjust to your response
    @SerializedName("error") val error: String? // Example nested data
)

data class RegistrationRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_type") val userType: String

)