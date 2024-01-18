package com.example.moneymate.model

data class Request(
    var accountNo: Long,
    var requestType: String,
    var requestStatus: String,
    var requestDate: String

){
    override fun toString(): String {
        return """
            Request:{
                Account Number: $accountNo
                Request Type: $requestType
                Request Status: $requestStatus
                Request Date: $requestDate
            }
        """
    }
}