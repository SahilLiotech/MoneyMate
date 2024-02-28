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

        val recipientAccountNum = recipientAccountNo.text.toString()
        val transferMoneyAmount = transferAmount.text.toString()
        try {
            transferBtn.setOnClickListener {
                if (recipientAccountNum != null && transferMoneyAmount != null && account != null) {
                    val recipientAccountNumber = recipientAccountNo.text.toString().toLong()
                    val transferMoney = transferAmount.text.toString().toInt()
                    transferMoney(account.accountNumber!!, recipientAccountNumber, transferMoney)
                } else {
                    Toast.makeText(applicationContext, "Some Error Occured", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: NumberFormatException) {
            transferAmount.error = "Please enter valid amount"
        }
    }

    private fun transferMoney(
        senderAccountNo: Long,
        recipientAccountNo: Long,
        transferAmount: Int
    ) {
        val dbHelper = TransactionTableHelper(this)
        val transactionType = "Transfer To $recipientAccountNo"
        val currentTotalAmount = dbHelper.getTotalAmount(senderAccountNo)
        val updatedTotalAmount = currentTotalAmount - transferAmount

        val transaction = Transaction(
            senderAccountNo,
            recipientAccountNo,
            transactionType,
            transferAmount,
            updatedTotalAmount,
            ""
        )

        val result = dbHelper.insertTransaction(transaction)

        if (result != -1L) {
            AlertDialog.Builder(this).create().apply {
                setTitle("Payment Success")
                setMessage("Your Transaction SucessFully Done...")
                setIcon(R.drawable.sucess)
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@TransferMoneyActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
                show()
            }
        } else {
            AlertDialog.Builder(this).create().apply {
                setTitle("Error occured")
                setIcon(R.drawable.error)
                setMessage("Please try again!")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}
