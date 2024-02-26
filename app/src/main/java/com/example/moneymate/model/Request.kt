package com.example.moneymate.model

data class Request(
    var requestId: Int? = null,
    var accountNo: Long? = null,
    var requestType: String? = null,
    var requestStatus: String? = null,
    var requestDate: String? = null

){
    override fun toString(): String {
        return """
            Request:{
                Request ID: $requestId
                Account Number: $accountNo
                Request Type: $requestType
                Request Status: $requestStatus
                Request Date: $requestDate
            }
        """
    }
}