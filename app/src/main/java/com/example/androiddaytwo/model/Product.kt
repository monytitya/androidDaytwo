package com.example.androiddaytwo.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val category: String,
    val storeName: String = "Velora Store",
    val isFavorite: Boolean = false
)

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

data class CartItem(
    val product: Product,
    val size: String,
    val quantity: Int
)
