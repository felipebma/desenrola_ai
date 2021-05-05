package com.example.desenrolaai.screens.borrows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desenrolaai.model.Borrow
import com.example.desenrolaai.model.Product
import com.example.desenrolaai.model.enums.BorrowStatus

class BorrowsViewModel : ViewModel() {

    private val _borrows = MutableLiveData<List<Borrow>>()
    val borrows: LiveData<List<Borrow>>
        get() = _borrows

    init {
        getBorrowList()
    }


    private fun getBorrowList() {
        val products = mutableListOf(
            Product(
                1,
                "Bicicleta",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                categories = listOf("Brinquedo", "Veículo") as MutableList<String>,
                pricePerDay = 10.0,
                latitude = -8.045238005758899,
                longitude = -34.911953177263506
            ),
            Product(
                1,
                "Celtinha 2006",
                "Carrin brabo",
                categories = listOf("Automóvel", "Veículo") as MutableList<String>,
                pricePerDay = 10.0,
                latitude = 0.0,
                longitude = 0.0
            )
        )

        _borrows.value = mutableListOf(
            Borrow(
                1,
                products[0],
                "fbma@cin.ufpe.br",
                "05/05/2021",
                "07/05/2021",
                BorrowStatus.PENDING
            ),
            Borrow(
                1,
                products[1],
                "lsm5@cin.ufpe.br",
                "07/05/2021",
                "08/05/2021",
                BorrowStatus.ACCEPTED
            )
        )
    }
}