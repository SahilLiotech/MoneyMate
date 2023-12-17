package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AccountInfoActivity : AppCompatActivity() {

    private lateinit var getName:TextView
    private lateinit var getAccountNO:TextView
    private lateinit var getGender:TextView
    private lateinit var getMobile:TextView
    private lateinit var getEmail:TextView
    private lateinit var getDob:TextView
    private lateinit var getPan:TextView
    private lateinit var getAddress:TextView
    private lateinit var getState:TextView
    private lateinit var getCity:TextView
    private lateinit var getPin:TextView
    private lateinit var getIfsc:TextView
    private lateinit var getBranch:TextView
    private lateinit var getNominee:TextView
    private lateinit var getNomineeAccount:TextView
    private lateinit var getNomineeAccountType:TextView
    private lateinit var getAccountOpen:TextView
    private lateinit var getAccountStatus:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_info)


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
        getNominee = findViewById(R.id.nominee_name)
        getNomineeAccount = findViewById(R.id.nominee_account_no)
        getNomineeAccountType = findViewById(R.id.account_type)
        getAccountOpen = findViewById(R.id.account_open)
        getAccountStatus = findViewById(R.id.account_status)


    }
}
