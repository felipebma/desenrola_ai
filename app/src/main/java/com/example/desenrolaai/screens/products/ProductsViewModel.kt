package com.example.desenrolaai.screens.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Product

class ProductsViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    init {
        getProductList()
    }


    private fun getProductList(){
        _products.value = mutableListOf(
            Product(
                1,
                "Bicicleta",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                categories = listOf<String>("Brinquedo", "Veículo"),
                pricePerDay = 10.0
            )
        )
    }

    /**
     * Navigation to ProductDetail fragment
     */

    private val _navigateToProductDetail = MutableLiveData<Product>()
    val navigateToProductDetail
        get() = _navigateToProductDetail

    fun onProductClicked(product: Product) {
        _navigateToProductDetail.value = product
    }
}