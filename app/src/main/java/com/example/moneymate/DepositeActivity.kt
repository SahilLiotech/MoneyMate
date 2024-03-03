package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

class DepositeActivity : AppCompatActivity() {

    private lateinit var receiverAccountNo: EditText
    private lateinit var amount: EditText
    private lateinit var depositeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposite)

        receiverAccountNo = findViewById(R.id.account_no)
        amount = findViewById(R.id.deposit_amount)
        depositeBtn = findViewById(R.id.submit_deposit_money)

        depositeBtn.setOnClickListener {
           val accountNumber = receiverAccountNo.text.toString().toLong()
           val depositAmount = amount.text.toString().toInt()

            val helper = TransactionTableHelper(this)
            val transaction = Transaction(
                accountNo = -1L,
                amount = depositAmount,
                receiverAccountNo = accountNumber,
                transactionType = "deposit"
            )
            val res = helper.addTransaction(transaction)
            if (!res.second) {
                Toast.makeText(this, res.first, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, res.first, Toast.LENGTH_SHORT).show()
            }
        }
    }

}