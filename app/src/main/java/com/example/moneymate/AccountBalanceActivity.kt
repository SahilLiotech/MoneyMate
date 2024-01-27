package com.example.moneymate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.moneymate.data.OpenAccountTableHelper

class AccountBalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_balance)

        val prefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val dbHelper = OpenAccountTableHelper(this)
        val account = dbHelper.getAccountDetailsByUserId(userId)

        val account_name=findViewById<TextView>(R.id.account_holder_name)
        val account_number=findViewById<TextView>(R.id.account_number)
        val account_amount=findViewById<TextView>(R.id.available_amount)

        if (account != null)
        {
            val(accountName,accountNumber,accountBalance) = account

            account_name.text = "Account Holder Name: "+accountName
            account_number.text = "Account Number: "+accountNumber
            account_amount.text = "Account Balance: "+accountBalance
        }
        else
        {
            Toast.makeText(applicationContext,"Something Went Wrong",Toast.LENGTH_SHORT).show()
        }

    }
}
