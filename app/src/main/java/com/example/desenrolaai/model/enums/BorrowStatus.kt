package com.example.desenrolaai.model.enums

enum class BorrowStatus(val status: String) {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    RUNNING("Running"),
    LATE("Late"),
    CLOSED("Closed")
}