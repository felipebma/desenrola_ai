package com.example.desenrolaai.screens.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Product
import java.lang.StringBuilder
import java.util.*

class ProductDetailViewModel: ViewModel() {
    private val _product = MutableLiveData<Product>()

    init{
        val categories = listOf<String>("Brinquedo", "Transporte")
        _product.value = Product(name = "Bicicleta",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            categories = categories,
            pricePerDay = 10.0,
            ownerEmail = "fbma@cin.ufpe.br"
        )
    }

    fun getName(): String{
        return _product.value?.name!!
    }

    fun getDescription(): String{
        return _product.value?.description!!
    }

    fun getCategories(): String{
        val str = StringJoiner("/")
        _product.value?.categories?.forEach {
            str.add(it)
        }
        return str.toString()
    }

    fun getPrice(): String{
        return "R$ %.2f".format(_product.value?.pricePerDay)
    }
}

/*
    var name: String? = null,
    var description: String? = null,
    val categories : List<String>? = ArrayList(),
    val images : List<String>? = ArrayList(),
    val pricePerDay : Double? = 0.0
 */