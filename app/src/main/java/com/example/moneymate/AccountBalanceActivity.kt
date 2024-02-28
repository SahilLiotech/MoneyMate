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

        val accountName = findViewById<TextView>(R.id.account_holder_name)
        val accountNumber = findViewById<TextView>(R.id.account_number)
        val accountAmount = findViewById<TextView>(R.id.available_amount)
        if (account != null) {
            accountName.text = "Account Holder Name: ${account.name.toString()}"
            accountNumber.text = "Account Number: ${account.accountNumber.toString()}"
            accountAmount.text = "Total Amount: ${account.amount.toString()}"
        } else {
            Toast.makeText(applicationContext, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }

    }
}
