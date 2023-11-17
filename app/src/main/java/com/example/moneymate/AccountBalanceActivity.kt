package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AccountBalanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_balance)

        val account_name=findViewById<TextView>(R.id.account_holder_name)
        val account_number=findViewById<TextView>(R.id.account_number)
        val account_amount=findViewById<TextView>(R.id.available_amount)

        account_name.text="Account Holder Name: Sahil Aslam Pathan"
        account_number.text="Account Number: 34066765643"
        account_amount.text="Account Balance: 2000"

    }
}
