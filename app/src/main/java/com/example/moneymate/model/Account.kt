package com.example.moneymate.model

data class Account(
    var accountNumber: Long? = null,
    var userid: Int = -1,
    var name: String? = null,
    var gender: String? = null,
    var mobileNumber: String? = null,
    var email: String? = null,
    var dob: String? = null,
    var pan: String? = null,
    var address: String? = null,
    var state: String? = null,
    var city: String? = null,
    var pincode: String? = null,
    var ifsc: String? = null,
    var branch: String? = null,
    var accountType: String? = null,
    var accountOpenDate: String? = null,
    var accountStatus: String? = null
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
