package com.example.androiddaytwo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androiddaytwo.model.CartItem
import com.example.androiddaytwo.model.Category
import com.example.androiddaytwo.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShopViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _newArrivals = MutableStateFlow<List<Product>>(emptyList())
    val newArrivals: StateFlow<List<Product>> = _newArrivals.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _categories.value = listOf(
            Category(1, "Men's outfit", "https://images.unsplash.com/photo-1516257984877-a03aae3acbc6?q=80&w=200&auto=format&fit=crop"),
            Category(2, "Woman's outfit", "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?q=80&w=200&auto=format&fit=crop"),
            Category(3, "Footwear", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=200&auto=format&fit=crop"),
            Category(4, "Accessories", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?q=80&w=200&auto=format&fit=crop")
        )

        _newArrivals.value = listOf(
            Product(1, "Modern Jacket", 210.0, "A stylish modern jacket for men.", "https://images.unsplash.com/photo-1591047139829-d91aecb6caea?q=80&w=400&auto=format&fit=crop", "Men's outfit"),
            Product(2, "Grey Casual Shoe", 120.0, "Comfortable and light casual shoes.", "https://images.unsplash.com/photo-1549298916-b41d501d3772?q=80&w=400&auto=format&fit=crop", "Footwear"),
            Product(3, "Running Sneakers", 150.0, "High-performance running sneakers.", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=400&auto=format&fit=crop", "Footwear"),
            Product(4, "Classic White Shirt", 45.0, "A crisp classic white shirt for any occasion.", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=400&auto=format&fit=crop", "Men's outfit"),
            Product(5, "Summer Dress", 85.0, "A light and breezy summer dress.", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?q=80&w=400&auto=format&fit=crop", "Woman's outfit"),
            Product(6, "Leather Handbag", 320.0, "Premium quality leather handbag.", "https://images.unsplash.com/photo-1584917865442-de89df76afd3?q=80&w=400&auto=format&fit=crop", "Accessories"),
            Product(7, "Denim Jeans", 95.0, "Durable and stylish slim-fit denim jeans.", "https://images.unsplash.com/photo-1542272604-787c3835535d?q=80&w=400&auto=format&fit=crop", "Men's outfit"),
            Product(8, "Sport Watch", 199.0, "Water-resistant digital sport watch.", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?q=80&w=400&auto=format&fit=crop", "Accessories"),
            Product(9, "Winter Coat", 450.0, "Heavy-duty winter coat for extreme cold.", "https://images.unsplash.com/photo-1539533377285-3c12ee3c5415?q=80&w=400&auto=format&fit=crop", "Men's outfit"),
            Product(10, "Casual T-Shirt", 25.0, "Simple and soft cotton t-shirt.", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=400&auto=format&fit=crop", "Men's outfit")
        )
    }

    fun getProductById(id: Int): Product? {
        return _newArrivals.value.find { it.id == id }
    }

    fun addToCart(product: Product, size: String, quantity: Int) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.product.id == product.id && it.size == size }
            if (existingItem != null) {
                currentItems.map {
                    if (it == existingItem) it.copy(quantity = it.quantity + quantity) else it
                }
            } else {
                currentItems + CartItem(product, size, quantity)
            }
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.update { currentItems ->
            currentItems.filter { it != cartItem }
        }
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        _cartItems.update { currentItems ->
            currentItems.map {
                if (it == cartItem) it.copy(quantity = newQuantity) else it
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}
