package com.example.moneymate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.model.Account

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var getName: TextView
    private lateinit var getAccountNO: TextView
    private lateinit var getGender: TextView
    private lateinit var getMobile: TextView
    private lateinit var getEmail: TextView
    private lateinit var getDob: TextView
    private lateinit var getPan: TextView
    private lateinit var getAddress: TextView
    private lateinit var getState: TextView
    private lateinit var getCity: TextView
    private lateinit var getPin: TextView
    private lateinit var getIfsc: TextView
    private lateinit var getBranch: TextView
    private lateinit var getAccountType: TextView
    private lateinit var getAccountOpen: TextView
    private lateinit var getAccountStatus: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)

        val prefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val dbHelper = OpenAccountTableHelper(this)
        val account = dbHelper.getAccountByUserId(userId)


        getName = findViewById(R.id.account_name)
        getAccountNO = findViewById(R.id.account_number)
        getGender = findViewById(R.id.gender)
        getMobile = findViewById(R.id.mobile_no)
        getEmail = findViewById(R.id.email)
        getDob = findViewById(R.id.dob)
        getPan = findViewById(R.id.pan_no)
        getAddress = findViewById(R.id.address)
        getState = findViewById(R.id.state)
        getCity = findViewById(R.id.city)
        getPin = findViewById(R.id.pin_code)
        getIfsc = findViewById(R.id.ifsc)
        getBranch = findViewById(R.id.branch)
        getAccountType = findViewById(R.id.account_type)
        getAccountOpen = findViewById(R.id.account_open)
        getAccountStatus = findViewById(R.id.account_status)

        displayAccountDetails(account)


    }

    private fun displayAccountDetails(account: Account?) {
        if (account != null) {
            getName.text = "A/C Holder Name: " + account.name
            getAccountNO.text = "A/C No: " + account.accountNumber.toString()
            getGender.text = "Gender: " + account.gender
            getMobile.text = "Mobile No: " + account.mobileNumber
            getEmail.text = "Email Id: " + account.email
            getDob.text = "Date Of Birth: " + account.dob
            getPan.text = "Pan Card No: " + account.pan
            getAddress.text = "Address: " + account.address
            getCity.text = "City: " + account.city
            getState.text = "State: " + account.state
            getPin.text = "Pin Code: " + account.pincode
            getIfsc.text = "IFSC Code: " + account.ifsc
            getBranch.text = "Branch Name: " + account.branch
            getAccountType.text = "A/C Type: " + account.accountType
            getAccountOpen.text = "Account Open Date: " + account.accountOpenDate
            getAccountStatus.text = "Account Status: " + account.accountStatus
        } else {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}
