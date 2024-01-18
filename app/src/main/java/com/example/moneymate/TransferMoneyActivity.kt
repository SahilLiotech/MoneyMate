package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

class TransferMoneyActivity : AppCompatActivity() {

    private lateinit var transferBtn:Button
    private lateinit var recipientAccountNo:EditText
    private lateinit var transferAmount:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_money_activity)

        transferBtn = findViewById(R.id.submit_transfer_money)
        recipientAccountNo = findViewById(R.id.recipient_account_no)
        transferAmount = findViewById(R.id.transfer_amount)

        transferBtn.setOnClickListener {
            val recipientAccountNum = recipientAccountNo.text.toString().toLong()
            val transferMoneyAmount = transferAmount.text.toString().toInt()

            if (recipientAccountNum != null && transferMoneyAmount != null)
            {
                transferMoney(recipientAccountNum,transferMoneyAmount)
            }
            else
            {
                Toast.makeText(applicationContext,"Some Error Occured",Toast.LENGTH_SHORT)
            }
        }
    }

    private fun transferMoney(recipientAccountNo:Long,transferAmount:Int){
        val dbHelper = TransactionTableHelper(this)
        val transactionType = "Transfer"
        val totalAmount = 500

        val transaction = Transaction(
              recipientAccountNo,
              transactionType,
              transferAmount,
            totalAmount - transferAmount,
             ""
        )

        val result = dbHelper.insertTransaction(transaction)

        if (result != -1L) {
            AlertDialog.Builder(this).create().apply {
                setTitle("Payment Success")
                setMessage("Your Transaction SucessFully Done...")
                setIcon(R.drawable.sucess)
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@TransferMoneyActivity,HomeActivity::class.java)
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
