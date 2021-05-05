package com.example.desenrolaai.screens.borrow_detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.BorrowDetailFragmentStatus
import com.example.desenrolaai.model.enums.BorrowStatus
import java.text.SimpleDateFormat
import java.util.*

class BorrowDetailViewModel(borrow: Borrow?, product: Product?) : ViewModel() {
    private val _borrow = MutableLiveData<Borrow>()
    private val _product = MutableLiveData<Product>()

    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    lateinit var status: BorrowDetailFragmentStatus

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
            val startDate = sdf.format(Date())
            val endDate = sdf.format(Date())
            status = BorrowDetailFragmentStatus.DETAIL
            return Borrow(
                1,
                product!!,
                "fbma@cin.ufpe.br",
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
    fun getEndDay(): String = _borrow.value?.endDate!!

    fun switchStatus(): Boolean {
        when (status) {
            BorrowDetailFragmentStatus.DETAIL -> {
                status = BorrowDetailFragmentStatus.CONFIRM
                Log.i("BorrowDetailViewModel", "Switch Status")
                return true
            }
            BorrowDetailFragmentStatus.CONFIRM -> {
                return saveBorrow()
            }
        }
        return false
    }

    fun saveBorrow(): Boolean {
        //TODO
        return true
    }

}