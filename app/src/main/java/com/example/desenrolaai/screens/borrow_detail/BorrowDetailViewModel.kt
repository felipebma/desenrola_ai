package com.example.desenrolaai.screens.borrow_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.User
import com.example.desenrolaai.model.enums.Status
import java.text.SimpleDateFormat
import java.util.*

class BorrowDetailViewModel: ViewModel() {
    private val _borrow = MutableLiveData<Borrow>()
    private val _product = MutableLiveData<Product>()

    val sdf = SimpleDateFormat("dd/MM/yyyy")
    var totalDays = 0

    init {
        _borrow.value = fetchBorrow()
        _product.value = fetchProduct()
    }

    private fun fetchProduct() : Product{
        return _borrow.value?.product!!
    }

    private fun fetchBorrow(): Borrow{
        val product = Product("Bicicleta",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            categories = listOf<String>("Brinquedo", "Veículo"),
            pricePerDay = 10.0,
            latitude = -8.04526287956983,
            longitude = -34.911943550439595
        )
        val user = User("fbma@cin.ufpe.br", "Felipe", "", 0.0, 0.0)
        val requester = User("lsm5@cin.ufpe.br", "Lucas", "", 0.0, 0.0)
        user.products!!.add(product)
        val date = sdf.parse("17/05/1994")
        return Borrow(product, user, requester, date!!, 0, Status.PENDING)
    }

    fun getName() = _product.value?.name
    fun getOwner() = _borrow.value?.owner?.name
    fun getCategories(): String{
        val categories = StringJoiner(" | ")
        for(category in _product.value?.categories!!) categories.add(category)
        return categories.toString()
    }
    fun getLatitude() = _product.value?.latitude
    fun getLongitude() = _product.value?.longitude
    fun getDescription() = _product.value?.description
    fun getPrice() = "R$ %.2f".format(_product.value?.pricePerDay)
    fun getStartDay() = sdf.format(_borrow.value?.initialDate)

}