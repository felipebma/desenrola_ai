package com.example.desenrolaai.model

data class User (
    val email : String? = null,
    val name : String? = null,
    var address : String? = null,
    var lat : Double? = 0.0,
    var longitute : Double? = 0.0,
    var products : List<Product>? = ArrayList()
)