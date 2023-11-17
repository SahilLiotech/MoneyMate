package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class TransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val transactions = arrayOf(
            arrayOf("2023-10-10 08:30:00", "Deposit", 1000),
            arrayOf("2023-10-11 15:45:00", "Withdraw", 500),
            arrayOf("2023-10-11 15:45:00", "Receive Money From #4066765644", 1000),
            arrayOf("2023-10-11 15:45:00", "Receive Money From #4066765645", 1000)
        )

        for (transaction in transactions) {
            val dateTime = transaction[0]
            val transactionType = transaction[1]
            val amount = transaction[2]

            val newRow = TableRow(this)

            val col1 = createTextView(dateTime as String)
            val col2 = createTextView(transactionType as String)
            val col3 = createTextView(amount.toString())

            newRow.addView(col1)
            newRow.addView(col2)
            newRow.addView(col3)

            tableLayout.addView(newRow)
        }
    }


    //function that create the texview for the content
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
