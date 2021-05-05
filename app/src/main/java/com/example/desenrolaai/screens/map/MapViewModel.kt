package com.example.desenrolaai.screens.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.User
import com.google.firebase.firestore.FirebaseFirestore

class MapViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>>
        get() = _products

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    var countFetch = 0
    private val _dataFetched = MutableLiveData<Boolean>()
    val dataFetched: LiveData<Boolean>
        get() = _dataFetched

    init {
        fetchUser()
        fetchProductList()
    }

    @Synchronized
    fun incrementFetched(){
        countFetch++
        if(countFetch == 2){
            _dataFetched.value = true
        }
    }

    private fun fetchUser() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("email", "lucas@gmail.com")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    _user.value = document.toObject(User::class.java)
                    Log.d("Firebase", "${document.id} => ${document.data}")
                }
                incrementFetched()
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }
    }

    private fun fetchProductList() {
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .whereNotEqualTo("name", "000")
            .get()
            .addOnSuccessListener { documents ->
                _products.value = mutableListOf<Product>()
                for (document in documents) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                    val product = document.toObject(Product::class.java)
                    _products.value?.add(product)
                }
                incrementFetched()
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }
    }
}