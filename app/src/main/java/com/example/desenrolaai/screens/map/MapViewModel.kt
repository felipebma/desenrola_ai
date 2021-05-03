package com.example.desenrolaai.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.User

class MapViewModel: ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init{
        _products.value = fetchProductList()
        _user.value = fetchUser()
    }

    private fun fetchUser(): User{
        return User(
            "fbma@cin.ufpe.br",
            "Felipe",
            "",
            -8.05558,
            -34.95136
        )
    }

    private fun fetchProductList() : List<Product>{
        val product = Product("Bicicleta",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            categories = listOf<String>("Brinquedo", "Veículo"),
            pricePerDay = 10.0,
            latitude = -8.04526287956983,
            longitude = -34.911943550439595
        )
        return listOf(product)
    }
}