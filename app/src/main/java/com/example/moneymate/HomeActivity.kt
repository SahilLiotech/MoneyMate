package com.example.moneymate

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.moneymate.data.RequestTableHelper
import com.example.moneymate.model.Request

class HomeActivity : AppCompatActivity() {
    private lateinit var openAccountButton: Button
    private lateinit var transferMoneyButton:Button
    private lateinit var checkBalance:Button
    private lateinit var checkTransaction:Button
    private lateinit var requestDebit:Button
    private lateinit var requestCheque:Button
    private lateinit var accountInfo:Button
    private lateinit var logout:Button
    private lateinit var heading:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("MoneyMate.Login", Context.MODE_PRIVATE)
        val uname = sharedPreferences.getString("uname", "user")

        val sharedPreferencesData = getSharedPreferences("MoneyMate.Account", Context.MODE_PRIVATE)
        val accountNumber = sharedPreferencesData.getLong("accountNo",0L)

        heading = findViewById(R.id.home_heading)
        heading.text = "Welcome $uname"

        openAccountButton = findViewById(R.id.open_account_btn)
        openAccountButton.setOnClickListener {
            val intent = Intent(this, OpenAccountActivity::class.java)

            startActivity(intent)
        }

        transferMoneyButton = findViewById(R.id.transfer_money_btn)
        transferMoneyButton.setOnClickListener {
            val intent = Intent(this@HomeActivity,TransferMoneyActivity::class.java)
            startActivity(intent)
        }

        checkBalance = findViewById(R.id.check_balance)
        checkBalance.setOnClickListener {
            val intent = Intent(this@HomeActivity,AccountBalanceActivity::class.java)
            startActivity(intent)
        }

        checkTransaction = findViewById(R.id.check_transaction)
        checkTransaction.setOnClickListener {
            val intent = Intent(this@HomeActivity,TransactionActivity::class.java)
            startActivity(intent)
        }

        requestDebit = findViewById(R.id.request_debit)
        requestDebit.setOnClickListener {

            AlertDialog.Builder(this@HomeActivity).apply {
                setTitle("Request For Debit Card")
                setMessage("Are you sure you want to make a request for debit card with account number: $accountNumber ?")
                setIcon(R.drawable.ic_info_black_24dp)

                    setPositiveButton("Yes"){
                        dialog, which->
                            insertDebitRequest(accountNumber)
                            dialog.dismiss()
                    }

                    setNegativeButton("No"){
                        dialog, which ->
                        dialog.dismiss()
                    }
                 show()
             }
        }

        requestCheque = findViewById(R.id.request_cheque)
        requestCheque.setOnClickListener {

                 AlertDialog.Builder(this@HomeActivity).apply {
                 setTitle("Request For Cheque Book")
                 setMessage("Are you sure you want to make a request for cheque book with account number: $accountNumber ?")
                 setIcon(R.drawable.ic_info_black_24dp)

                 setPositiveButton("Yes"){
                        dialog, which ->
                                AlertDialog.Builder(this@HomeActivity) .create().apply {
                                setMessage("You request for cheque book has been successfully sent. You'll receive it soon.")
                                setButton(DialogInterface.BUTTON_POSITIVE,"OK"){
                                    dialog, which ->  dialog.dismiss()
                        }
                        show()
                     }
                 }

                 setNegativeButton("No"){
                        dialog, which ->
                    dialog.dismiss()
                }

                show()
            }
        }

        accountInfo = findViewById(R.id.account_info)
        accountInfo.setOnClickListener {
            val intent = Intent(this@HomeActivity,AccountInfoActivity::class.java)
            startActivity(intent)
        }

        logout = findViewById(R.id.logout)
        logout.setOnClickListener {

            AlertDialog.Builder(this).apply {
                setTitle("Logout From MoneyMate")
                setMessage("Are you sure you want to logout from the MoneyMate?")
                setIcon(R.drawable.ic_info_black_24dp)
                setPositiveButton("Yes"){
                   dialog, which ->
                    val sharedPreferences = getSharedPreferences("MoneyMate.Login", Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor = sharedPreferences.edit()
                    editor.clear()
                    editor.commit()

                    val intent = Intent(this@HomeActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
                setNegativeButton("No"){ dialog, _ ->
                            dialog.dismiss()
                }
                show()
            }
        }

        val debug = findViewById<Button>(R.id.debug)
        debug.setOnClickListener {
            val intent = Intent(this@HomeActivity,DebugActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertDebitRequest(accountNumber:Long){
        val dbHelper = RequestTableHelper(this@HomeActivity)
        val request = Request(
            0,
             accountNumber,
            "Debit Card",
            "",
            "Pending"
        )
        val success = dbHelper.insertRequest(request)

        if (success) {
            AlertDialog.Builder(this@HomeActivity).create().apply {
                setMessage("Your request for a Debit Card has been successfully sent. You'll receive it soon.")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { msgDialog, _ ->
                    msgDialog.dismiss()
                }
                show()
            }
        } else {
            AlertDialog.Builder(this@HomeActivity).create().apply {
                setMessage("Error occurred while processing your request. Please try again.")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { msgDialog, _ ->
                    msgDialog.dismiss()
                }
                show()
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Exit MoneyMate")
            setMessage("Are you sure you want to exit?")
            setIcon(R.drawable.ic_info_black_24dp)
            setPositiveButton("Yes") { _ , _ ->
                finishAffinity()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }
}
