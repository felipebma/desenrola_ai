package com.example.desenrolaai.screens.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProductsViewModel : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>()
    val products: LiveData<MutableList<Product>>
        get() = _products

    var dataFetched = MutableLiveData<Boolean>()

    init {
        getProductList()
    }


    private fun getProductList() {

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        db.collection("products")
            .whereEqualTo("ownerEmail", currentUser.email)
            .get()
            .addOnSuccessListener { documents ->
                _products.value = mutableListOf()
                for (document in documents) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                    val product = document.toObject(Product::class.java)
                    _products.value?.add(product)
                }
                dataFetched.value = true
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }

    }
}