package com.example.farmerapp.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date

data class Order(
    val orderid: Int? = null,
    val farmerid: Int,
    val buyerid: Int,
    @SuppressLint("SimpleDateFormat")
    val orderdate: String? = SimpleDateFormat("yyyy-MM-dd").format(Date()),
    val orderstatus: String,
    val totalamount: Int,
    val totalprice: Float,
    val productname: String,
)

data class Message(
    val chatid: Int?,
//    val senderId: Int,
    val sendertype: String?,
    val messagetext: String?,
    val sentat: String?,
    val error: String?
)

data class Chat(
    val chatid: Int? = null,
    val buyerid: Int,
    val farmerid: Int,
    val status: String
)

data class Cart(
    val cartid: Int? = null,
    val productid: Int,
    val productname: String,
    val productprice: String,
    val buyerid: Int,
    val quantity: Int,
    val farmerid: Int,
    val status: String,
)

data class Id(
    @SerializedName("id") val id: Int
)

data class AddUpdateRequest(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Float,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("farmer_id") val farmerID: Int? = null,
)


data class Product(
    @SerializedName("productid") val productID: Int = -1,
    val name: String,
    val price: String,
    val quantity: String,
    @SerializedName("farmerid") val farmerID: Int = -1,
    val image: String? = null ,
)



data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("user_type") val userType: String // Make sure these match your API
)

data class RequestResponse(
    @SerializedName("message") val message: String?, // Example field, adjust to your response
    @SerializedName("name") val name: String?
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
