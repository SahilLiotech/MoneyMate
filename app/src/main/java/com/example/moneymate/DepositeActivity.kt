package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

class DepositeActivity : AppCompatActivity() {

    private lateinit var accountNo: EditText
    private lateinit var amount: EditText
    private lateinit var depositeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposite)

        accountNo = findViewById(R.id.account_no)
        amount = findViewById(R.id.deposit_amount)
        depositeBtn = findViewById(R.id.submit_deposit_money)

        depositeBtn.setOnClickListener {
           val accountNumber = accountNo.text.toString().toLong()
           val depositAmount = amount.text.toString().toInt()

           depositMoney(accountNumber,depositAmount)
        }
    }

    private fun depositMoney(
        accountNo:Long,
        depositAmount: Int
    ) {
        val dbHelper = TransactionTableHelper(this)
        val currentTotalAmount = dbHelper.getTotalAmount(accountNo)
        val updatedTotalAmount = currentTotalAmount + depositAmount

        val transaction = Transaction(
            -1,
            accountNo,
            "Deposit",
            depositAmount,
            updatedTotalAmount,
            ""
        )

        val result = dbHelper.insertTransaction(transaction)

        if (result != -1L) {
            AlertDialog.Builder(this).create().apply {
                setTitle("Deposit")
                setMessage("Deposit Suceessfully...")
                setIcon(R.drawable.sucess)
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@DepositeActivity, StaffDashboardActivity::class.java)
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