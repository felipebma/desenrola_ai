package com.example.desenrolaai.screens.borrows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.enums.BorrowStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class BorrowsViewModel : ViewModel() {

    private val _borrows = MutableLiveData<MutableList<Borrow>>()
    val borrows: LiveData<MutableList<Borrow>>
        get() = _borrows

    var dataFetched = MutableLiveData<Boolean>()

    init {
        getBorrowList()
    }


    private fun getBorrowList() {

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        db.collection("borrows")
            .whereEqualTo("requesterEmail", currentUser.email)
            .get()
            .addOnSuccessListener { documents ->
                _borrows.value = mutableListOf()
                for (document in documents) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                    val borrow = document.toObject(Borrow::class.java)
                    updateStatus(borrow)
                    _borrows.value?.add(borrow)
                }
                dataFetched.value = true
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents: ", exception)
            }
    }

    private fun updateStatus(borrow: Borrow){
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        when(borrow.status){
            BorrowStatus.ACCEPTED -> {
                if(sdf.parse(borrow.endDate).after(Date())){
                    borrow.status = BorrowStatus.LATE
                    return
                }
                if(sdf.parse(borrow.startDate).before(Date())){
                    borrow.status = BorrowStatus.RUNNING
                    return
                }
            }
            BorrowStatus.RUNNING -> {
                if(sdf.parse(borrow.endDate).after(Date())){
                    borrow.status = BorrowStatus.LATE
                    return
                }
            }
        }
    }
}