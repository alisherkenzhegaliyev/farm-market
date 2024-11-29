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
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_type") val userType: String,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("address") val address: String? = null,
    @SerializedName("preferred_payment_method") val ppm: String? = null,
    @SerializedName("farmlocation") val farmAddress: String? = null,
    @SerializedName("farm_size") val farmSize: String? = null,
    @SerializedName("crop_type") val cropTypes: String? = null,
    @SerializedName("governmentid") val govtId: String? = null,
)

//<<<<<<< HEAD
//=======
data class FarmerRegistrationRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_type") val userType: String,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("farmlocation") val farmAddress: String,
    @SerializedName("farm_size") val farmSize: String,
    @SerializedName("crop_type") val cropTypes: String,
    @SerializedName("governmentid") val govtId: String

)
//>>>>>>> d4cf4e40251fa1721b456ba07e68bb92c468ea27
