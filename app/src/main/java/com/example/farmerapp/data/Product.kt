package com.example.farmerapp.data

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val farm: String,
    val description: String,
    val category: String,
    val rating: Int
)
