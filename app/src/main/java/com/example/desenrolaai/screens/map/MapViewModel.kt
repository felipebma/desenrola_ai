package com.example.desenrolaai.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.User

class MapViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>>
        get() = _products

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        fetchUser()
        fetchProductList()
    }

    private fun fetchUser() {
        _user.value = User(
            email = "fbma@cin.ufpe.br",
            name = "Felipe",
            latitude = -8.05558,
            longitude = -34.95136
        )
    }

    private fun fetchProductList() {
        val product = Product(
            1,
            name = "Bicicleta",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            categories = listOf("Brinquedo", "Veículo") as MutableList<String>,
            pricePerDay = 10.0,
            latitude = -8.04526287956983,
            longitude = -34.911943550439595,
            ownerEmail = "lsm5@cin.ufpe.br",
            ownerName = "Lucas Mendonça"
        )
        _products.value = listOf(product) as MutableList<Product>
    }
}