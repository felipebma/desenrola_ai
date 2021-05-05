package com.example.desenrolaai.screens.product_detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.ProductDetailStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ProductDetailViewModel(product: Product, status: ProductDetailStatus) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    val requestSaveSuccess = MutableLiveData<Boolean>()

    val requestSaveFailed = MutableLiveData<Boolean>()

    private val _editProduct = MutableLiveData<Product>()

    private var _status = status

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    init {
        _product.value = product
        _product.value?.ownerEmail = currentUser.email
        _editProduct.value = product.copy()
    }

    fun switchStatus(){
        when (_status) {
            ProductDetailStatus.DETAIL -> {
                _status = ProductDetailStatus.EDIT
            }
            ProductDetailStatus.EDIT -> {
                saveEdit()
            }
            ProductDetailStatus.ADD -> {
                saveAdd()
            }
        }
    }

    fun updateProduct() {
        _product.value = _editProduct.value?.copy()
    }

    private fun saveEdit() {
        if(!validProduct()) return
        updateProduct()
        db.collection("products").document(_product.value?.id!!).set(_product).addOnSuccessListener {
            requestSaveSuccess.value = true
        }.addOnCanceledListener {
            requestSaveFailed.value = true
        }
    }

    private fun saveAdd(){
        if(!validProduct()) return
        updateProduct()
        Log.i("Products", "SaveAdd")
        db.collection("products").add(_product).addOnSuccessListener {
            requestSaveSuccess.value = true
        }.addOnCanceledListener {
            requestSaveFailed.value = true
        }
    }

    fun validProduct(): Boolean{
        return validName() && validDescription() && validPrice() && validLatitude() && validLongitude()
    }

    private fun validName(): Boolean{
        return _editProduct.value?.name!!.isNotEmpty()
    }

    private fun validDescription(): Boolean{
        return _editProduct.value?.description!!.isNotEmpty()
    }

    private fun validPrice(): Boolean{
        return _editProduct.value?.pricePerDay!! > 0
    }

    private fun validLatitude(): Boolean{
        return _editProduct.value?.latitude!! <= 90 && _editProduct.value?.latitude!! >= -90
    }

    private fun validLongitude(): Boolean{
        return _editProduct.value?.latitude!! <= 180 && _editProduct.value?.latitude!! >= -180
    }

    fun getStatus() = _status
    fun setStatus(status: ProductDetailStatus) { _status = status}


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

    fun getEditName() = _editProduct.value?.name!!
    fun setEditName(name: String) {
        _editProduct.value?.name = name
    }

    fun getEditDescription() = _editProduct.value?.description!!
    fun setEditDescription(description: String) {
        _editProduct.value?.description = description
    }

    fun getEditCategories(): String {
        val str = StringJoiner(" | ")
        _editProduct.value?.categories?.forEach {
            str.add(it)
        }
        return str.toString()
    }

    fun setEditCategories(categories: String) {
        _editProduct.value?.categories = categories.split(" | ") as MutableList<String>
    }

    fun getEditPrice() = "%.2f".format(_editProduct.value?.pricePerDay)
    fun setEditPrice(price: String) {
        _editProduct.value?.pricePerDay = if(price.isEmpty()) 0.0 else price.toDouble()
    }

    fun getEditLatitude() = _editProduct.value?.latitude.toString()
    fun setEditLatitude(latitude: String) {
        _editProduct.value?.latitude = if(latitude.isEmpty() || latitude == "-") 0.0 else latitude.toDouble()
    }

    fun getEditLongitude() = _editProduct.value?.longitude.toString()
    fun setEditLongitude(longitude: String) {
        _editProduct.value?.longitude = if(longitude.isEmpty() || longitude == "-") 0.0 else longitude.toDouble()
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
