package com.example.moneymate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.TransactionTableHelper

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val prefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val dbHelper = OpenAccountTableHelper(this)
        val account = dbHelper.getAccountByUserId(userId)

        if (account != null) {
            val dbHelper1 = TransactionTableHelper(this)
            val transactions = dbHelper1.getTransactionDetail(account.accountNumber)

            for (transaction in transactions) {
                val dateTime = transaction.doneAt
                val transactionType = transaction.transactionType
                val amount = transaction.amount

                val newRow = TableRow(this)

                val col1 = createTextView(dateTime)
                val col2 = createTextView(transactionType)
                val col3 = createTextView(amount.toString())

                newRow.addView(col1)
                newRow.addView(col2)
                newRow.addView(col3)

                tableLayout.addView(newRow)
            }
        }
    }

    // Function that creates the TextView for the content
    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(18, 16, 18, 16)
        textView.setTextColor(resources.getColor(R.color.blackText))
        textView.textSize = 16f
        textView.setBackgroundResource(R.drawable.tablerow_blackborder)
        return textView
    }
}
