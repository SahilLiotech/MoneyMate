package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

class DepositeActivity : AppCompatActivity() {

    private lateinit var receiverAccountNo: EditText
    private lateinit var amount: EditText
    private lateinit var depositeBtn: Button
    private lateinit var senderAccountNo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposite)

        receiverAccountNo = findViewById(R.id.account_no)
        amount = findViewById(R.id.deposit_amount)
        depositeBtn = findViewById(R.id.submit_deposit_money)
        senderAccountNo = findViewById(R.id.sender_account_number)

        depositeBtn.setOnClickListener {
           val accountNumber = receiverAccountNo.text.toString().toLong()
            val senderAccountNumber = senderAccountNo.text.toString().toLong()
           val depositAmount = amount.text.toString().toInt()

            val helper = TransactionTableHelper(this)
            val transaction = Transaction(
                accountNo = senderAccountNumber,
                amount = depositAmount,
                doneAt = "",
                receiverAccountNo = accountNumber,
                transactionType = "deposit"
            )
            val res = helper.addTransaction(transaction)
            if (!res.second) {
                Toast.makeText(this, res.first, Toast.LENGTH_SHORT).show()
                // 517061984332
                    // 672224781942
                finish()
            } else {
                Toast.makeText(this, res.first, Toast.LENGTH_SHORT).show()
            }
        }
    }

}