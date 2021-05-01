package com.example.desenrolaai.model

data class Product(
    var name: String? = null,
    var description: String? = null,
    val categories : List<String>? = ArrayList(),
    val images : List<String>? = ArrayList(),
    val pricePerDay : Double? = 0.0
)