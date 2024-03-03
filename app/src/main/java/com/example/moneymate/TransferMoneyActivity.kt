package com.example.moneymate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

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

            if (recipientAccountNum.isBlank()) {
                Toast.makeText(this, "Receiver's account number must not be blank", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (transferMoneyAmount.isBlank()) {
                Toast.makeText(this, "Amount must not be blank", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (account == null) {
                Toast.makeText(this, "You are not eligible to make this transaction", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val recipientAccountNumber = recipientAccountNo.text.toString().toLong()
            val transferMoney = transferAmount.text.toString().toInt()
            val senderAmount = OpenAccountTableHelper(this)
                .getAmountOf(account.accountNumber.toString())

            if (senderAmount!! < transferMoney) {
                Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val transaction = Transaction(
                account.accountNumber,
                recipientAccountNumber,
                "transfer",
                transferMoney
            )

            val helper = TransactionTableHelper(this)
            val (msg, status) = helper.addTransaction(transaction)
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            if (status) finish()
        }
    }
}
