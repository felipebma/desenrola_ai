package com.example.desenrolaai.model

import com.example.desenrolaai.model.enums.BorrowStatus
import java.util.*

data class Borrow (
    val product: Product,
    val owner: User,
    val requester: User,
    val initialDate: Date,
    val duration: Int,
    var status: BorrowStatus
)