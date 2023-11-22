package com.example.moneymate.model

data class Account(
    var accountNumber: Long,
    var registrationId: Int = -1,
    var name: String,
    var gender: String,
    var mobileNumber: String,
    var email: String,
    var dob: String,
    var pan: String,
    var address: String,
    var state: String,
    var city: String,
    var pincode: String,
    var ifsc: String,
    var branch: String,
    var nomineeName: String,
    var nomineeAccount: String,
    var nomineeAccountType: String,
    var accountOpenDate: String,
    var accountStatus:String
)
