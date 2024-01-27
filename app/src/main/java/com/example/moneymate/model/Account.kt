package com.example.moneymate.model

data class Account(
    var accountNumber: Long,
    var userid: Int = -1,
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
    var accountType: String,
    var accountOpenDate: String,
    var accountStatus:String
){
    override fun toString(): String {
        return """
           Account:{
                accountNumber:$accountNumber
                userid:$userid
                name:$name
                gender:$gender
                mobile:$mobileNumber
                email:$email
                dob:$dob
                pan:$pan
                address:$address
                state:$state
                city:$city
                pincode:$pincode
                ifsc:$ifsc
                branch:$branch
                accountType:$accountType
                accountOpendate:$accountOpenDate
                accountStatus:$accountStatus
           }
           """
    }
}
