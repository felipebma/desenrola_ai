package com.example.desenrolaai.model

data class User(
    var email: String? = null,
    var name: String? = null,
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var products: MutableList<Product>? = ArrayList()
)