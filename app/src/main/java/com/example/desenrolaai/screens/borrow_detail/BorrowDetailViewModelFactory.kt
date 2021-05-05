package com.example.desenrolaai.screens.borrow_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product

class BorrowDetailViewModelFactory(private val borrow: Borrow?, private val product: Product?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BorrowDetailViewModel::class.java)) {
            return BorrowDetailViewModel(borrow, product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}