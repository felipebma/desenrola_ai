package com.example.desenrolaai.screens.borrow_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.BorrowDetailFragmentStatus
import com.example.desenrolaai.model.enums.BorrowStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class BorrowDetailViewModel(borrow: Borrow?, product: Product?) : ViewModel() {
    private val _borrow = MutableLiveData<Borrow>()
    private val _product = MutableLiveData<Product>()

    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    var status: BorrowDetailFragmentStatus
    var requestAccepted = MutableLiveData<Boolean>()
    var requestRefused = MutableLiveData<Boolean>()

    init {
        status = BorrowDetailFragmentStatus.DETAIL
        _borrow.value = fetchBorrow(borrow, product)
        _product.value = fetchProduct()
    }

    private fun fetchProduct(): Product {
        return _borrow.value?.product!!
    }

    private fun fetchBorrow(borrow: Borrow?, product: Product?): Borrow {
        if (borrow == null) {
            val c = Calendar.getInstance()
            c.setTime(Date())
            c.add(Calendar.DATE, 1)
            val startDate = sdf.format(c.time)
            c.add(Calendar.DATE, 1)
            val endDate = sdf.format(c.time)
            val auth = FirebaseAuth.getInstance().currentUser
            status = BorrowDetailFragmentStatus.DETAIL
            return Borrow(
                0,
                product!!,
                auth.email,
                startDate,
                endDate,
                BorrowStatus.PENDING
            )
        }
        status = BorrowDetailFragmentStatus.READ
        return borrow
    }

    fun getName() = _product.value?.name
    fun getOwner() = _product.value?.ownerName
    fun getCategories(): String {
        val categories = StringJoiner(" | ")
        for (category in _product.value?.categories!!) categories.add(category)
        return categories.toString()
    }

    fun getLatitude() = _product.value?.latitude
    fun getLongitude() = _product.value?.longitude
    fun getDescription() = _product.value?.description
    fun getPrice() = "R$ %.2f".format(_product.value?.pricePerDay)
    fun getStartDay(): String = _borrow.value?.startDate!!
    fun setStartDay(date: String) {
        _borrow.value?.startDate = date
    }

    fun getEndDay(): String = _borrow.value?.endDate!!
    fun setEndDay(date: String) {
        _borrow.value?.endDate = date
    }

    fun getBorrowStatus() = _borrow.value?.status

    fun switchStatus() {
        when (status) {
            BorrowDetailFragmentStatus.DETAIL -> {
                status = BorrowDetailFragmentStatus.CONFIRM
            }
            BorrowDetailFragmentStatus.CONFIRM -> {
                saveBorrow()
            }
        }
    }

    private fun saveBorrow() {
        if (isValidBorrow()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("borrows").add(_borrow.value!!).addOnSuccessListener {
                requestAccepted.value = true
            }.addOnCanceledListener {
                requestRefused.value = true
            }
        }
    }

    fun isValidBorrow(): Boolean {
        val start: Date
        val end: Date
        Log.i("StartDate", _borrow.value?.startDate!!.toString())
        Log.i("EndDate", _borrow.value?.endDate!!.toString())
        try {
            start = sdf.parse(_borrow.value?.startDate!!)!!
            end = sdf.parse(_borrow.value?.endDate!!)!!
        } catch (e: Exception) {
            return false
        }
        return !start.before(Date()) && end.after(start)
    }

}