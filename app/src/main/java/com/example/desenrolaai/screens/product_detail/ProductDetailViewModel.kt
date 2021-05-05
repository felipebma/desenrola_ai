package com.example.desenrolaai.screens.product_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.ProductDetailStatus
import java.util.*

class ProductDetailViewModel(product: Product, status: ProductDetailStatus) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    private val _editProduct = MutableLiveData<Product>()

    private var _status = status

    init {
        _product.value = product
        _editProduct.value = product.copy()
    }

    fun switchStatus(confirm: Boolean): Boolean {
        when (_status) {
            ProductDetailStatus.DETAIL -> {
                if (confirm) _status = ProductDetailStatus.EDIT
                return true
            }
            ProductDetailStatus.EDIT -> {
                if (confirm) return saveEdit()
                _status = ProductDetailStatus.DETAIL
                return true
            }
            ProductDetailStatus.ADD -> {
                if (confirm) return saveAdd()
                return true
            }
        }
    }

    private fun saveEdit(): Boolean {
        //TODO
        return true
    }

    private fun saveAdd(): Boolean {
        //TODO
        return true
    }

    fun getStatus() = _status


    fun getName() = _product.value?.name!!
    fun getDescription() = _product.value?.description!!
    fun getCategories(): String {
        val str = StringJoiner(" | ")
        _product.value?.categories?.forEach {
            str.add(it)
        }
        return str.toString()
    }

    fun getFormattedPrice() = "R$ %.2f".format(_product.value?.pricePerDay)
    fun getLatitude() = _product.value?.latitude.toString()
    fun getLongitude() = _product.value?.longitude.toString()

    fun getEditName() = _product.value?.name!!
    fun setEditName(name: String) {
        _product.value?.name = name
    }

    fun getEditDescription() = _product.value?.description!!
    fun setEditDescription(description: String) {
        _product.value?.description = description
    }

    fun getEditCategories(): String {
        val str = StringJoiner(" | ")
        _product.value?.categories?.forEach {
            str.add(it)
        }
        return str.toString()
    }

    fun setEditCategories(categories: String) {
        _product.value?.categories = categories.split(" | ") as MutableList<String>
    }

    fun getEditPrice() = "%.2f".format(_product.value?.pricePerDay)
    fun setEditPrice(price: String) {
        _product.value?.pricePerDay = price.toDouble()
    }

    fun getEditLatitude() = _product.value?.latitude.toString()
    fun setEditLatitude(latitude: String) {
        _product.value?.latitude = latitude.toDouble()
    }

    fun getEditLongitude() = _product.value?.longitude.toString()
    fun setEditLongitude(longitude: String) {
        _product.value?.longitude = longitude.toDouble()
    }

    class ProductDetailViewModelFactory(
        private val product: Product,
        private val status: ProductDetailStatus
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(product, status) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}
