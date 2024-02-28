package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Transaction

class WithdrawActivity : AppCompatActivity() {
    private lateinit var accountNumber: EditText
    private lateinit var amount: EditText
    private lateinit var withdrawBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        accountNumber = findViewById(R.id.account_no)
        amount = findViewById(R.id.withdraw_amount)
        withdrawBtn = findViewById(R.id.submit_withdraw_money)

        withdrawBtn.setOnClickListener {
            val accountNo = accountNumber.text.toString().toLong()
            val withdrawAmount = amount.text.toString().toInt()

            val helper = TransactionTableHelper(this)
            val transaction = Transaction(
                -1,
                amount = withdrawAmount,
                doneAt = "",
                receiverAccountNo = accountNo,
                transactionType = "withdraw"
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