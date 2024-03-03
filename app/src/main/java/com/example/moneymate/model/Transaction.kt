package com.example.moneymate.model

data class Transaction(
    var accountNo:Long? = null,
    var receiverAccountNo:Long? = null,
    var transactionType:String? = null,
    var amount:Int? = null,
    var doneAt:String? = null
) {
    override fun toString(): String {
        return """
           Transaction:{
                AccountNo: $accountNo
                Receiver AccountNo: $receiverAccountNo
                Transaction Type: $transactionType
                Amount: $amount
                Done AT: $doneAt
            }
        """
    }
}