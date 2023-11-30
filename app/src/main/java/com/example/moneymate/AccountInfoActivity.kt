package com.example.moneymate

import android.content.Context
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

        val sharedPreference = getSharedPreferences("MoneyMate.Account", Context.MODE_PRIVATE)

        val accountNo = sharedPreference.getLong("accountNo", 0L)
        val name = sharedPreference.getString("name", "")
        val gender = sharedPreference.getString("gender", "")
        val mobile = sharedPreference.getString("mobile", "")
        val email = sharedPreference.getString("email", "")
        val dob = sharedPreference.getString("dob", "")
        val pan = sharedPreference.getString("pan", "")
        val address = sharedPreference.getString("address", "")
        val state = sharedPreference.getString("state", "")
        val city = sharedPreference.getString("city", "")
        val pin = sharedPreference.getString("pin", "")
        val ifsc = sharedPreference.getString("ifsc", "")
        val branch = sharedPreference.getString("branch", "")
        val nomineeName = sharedPreference.getString("nomineename", "")
        val nomineeAccount = sharedPreference.getString("nomineeaccount", "")
        val nomineeType = sharedPreference.getString("nomineetype", "")
        val accountOpenDate = sharedPreference.getString("accountopen", "")
        val accountStatus = sharedPreference.getString("accountstatus", "")

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


        getName.text = "Name: $name"
        getAccountNO.text = "Account No: $accountNo"
        getGender.text = "Gender : $gender"
        getMobile.text = "Mobile : $mobile"
        getEmail.text = "Email : $email"
        getDob.text = "Dob : $dob"
        getPan.text = "Pan : $pan"
        getAddress.text = "Address : $address"
        getState.text = "State : $state"
        getCity.text = "City : $city"
        getPin.text = "Pin : $pin"
        getIfsc.text = "Ifsc : $ifsc"
        getBranch.text = "Branch : $branch"
        getNominee.text = "Nominee Name: $nomineeName"
        getNomineeAccount.text = "Nominee Account No: $nomineeAccount"
        getNomineeAccountType.text = "Nominee Account Type: $nomineeType"
        getAccountOpen.text = "Account Open Date: $accountOpenDate"
        getAccountStatus.text = "Account Status: $accountStatus"
    }
}
