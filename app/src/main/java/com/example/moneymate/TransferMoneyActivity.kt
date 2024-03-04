package com.example.moneymate

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Account
import com.example.moneymate.model.Transaction
import java.lang.NumberFormatException

class TransferMoneyActivity : AppCompatActivity() {

    private lateinit var transferBtn: Button
    private lateinit var recipientAccountNo: EditText
    private lateinit var transferAmount: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_money_activity)

        val prefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val dbHelper = OpenAccountTableHelper(this)
        val account = dbHelper.getAccountByUserId(userId)

        transferBtn = findViewById(R.id.submit_transfer_money)
        recipientAccountNo = findViewById(R.id.recipient_account_no)
        transferAmount = findViewById(R.id.transfer_amount)

        transferBtn.setOnClickListener {
            val recipientAccountNum = recipientAccountNo.text.toString()
            val transferMoneyAmount = transferAmount.text.toString()
            if (recipientAccountNum.trim() == "" && transferMoneyAmount.trim() == "") {
                Toast.makeText(this, "Please enter number", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (account == null) {
                Toast.makeText(this, "You ain't valid, nigger", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val recipientAccountNumber = recipientAccountNo.text.toString().toLong()
            val transferMoney = transferAmount.text.toString().toInt()

            val helper = TransactionTableHelper(this)
            val transaction = Transaction()
            transaction.accountNo = account.accountNumber
            transaction.amount = transferMoney
            transaction.receiverAccountNo = recipientAccountNumber
            transaction.transactionType = "transfer"
            val (msg, status) = helper.addTransaction(transaction)

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            if (status) finish()
        }
    }
}
