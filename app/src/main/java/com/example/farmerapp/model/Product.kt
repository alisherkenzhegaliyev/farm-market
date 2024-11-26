package com.example.farmerapp.model

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
