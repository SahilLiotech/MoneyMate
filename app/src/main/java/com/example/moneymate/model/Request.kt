package com.example.moneymate.model

data class Request(
    var requestId: Int,
    var accountNo: Long,
    var requestType: String,
    var requestDate: String,
    var requestStatus: String
)