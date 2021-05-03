package com.example.desenrolaai.screens.product_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.ProductDetailStatus
import java.util.*

class ProductDetailViewModel(product: Product, status: ProductDetailStatus) : ViewModel() {
    private val _product = MutableLiveData<Product>()

    private var _status = status

    init {
        _product.value = product
    }

    fun switchStatus(confirm: Boolean): Boolean{
        when(_status){
            ProductDetailStatus.DETAIL -> {
                if(confirm) _status = ProductDetailStatus.EDIT
                return true
            }
            ProductDetailStatus.EDIT -> {
                if(confirm) return saveEdit()
                _status = ProductDetailStatus.DETAIL
                return true
            }
            ProductDetailStatus.ADD -> {
                if(confirm) return saveAdd()
                return true
            }
        }
    }

    private fun saveEdit(): Boolean{
        //TODO
        return true
    }

    private fun saveAdd(): Boolean{
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
    fun getPrice() = "R$ %.2f".format(_product.value?.pricePerDay)
    fun getLatitude() = _product.value?.latitude.toString()
    fun getLongitude() = _product.value?.longitude.toString()

    class ProductDetailViewModelFactory(private val product: Product, private val status: ProductDetailStatus) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
                return ProductDetailViewModel(product, status) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}
