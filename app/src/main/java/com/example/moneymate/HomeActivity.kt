package com.example.moneymate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.RequestTableHelper
import com.example.moneymate.model.Request

class HomeActivity : AppCompatActivity() {
    private lateinit var openAccountButton: Button
    private lateinit var transferMoneyButton: Button
    private lateinit var checkBalance: Button
    private lateinit var checkTransaction: Button
    private lateinit var requestDebit: Button
    private lateinit var requestCheque: Button
    private lateinit var accountInfo: Button
    private lateinit var changePassword: Button
    private lateinit var logout: Button
    private lateinit var heading: TextView
    private lateinit var accountNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        heading = findViewById(R.id.home_heading)
        openAccountButton = findViewById(R.id.open_account_btn)
        transferMoneyButton = findViewById(R.id.transfer_money_btn)
        checkBalance = findViewById(R.id.check_balance)
        checkTransaction = findViewById(R.id.check_transaction)
        requestDebit = findViewById(R.id.request_debit)
        requestCheque = findViewById(R.id.request_cheque)
        accountInfo = findViewById(R.id.account_info)
        logout = findViewById(R.id.logout)
        changePassword = findViewById(R.id.changepassword)

        val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val uname = sharedPreferences.getString("uname", "user")

        val prefs: SharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val helper = OpenAccountTableHelper(this)

        for (record in helper.getAccountList()) {
            Log.d("req-dbg", record.toString())
        }

        val dbHelper = OpenAccountTableHelper(this)
        val account = dbHelper.getAccountByUserId(userId)
        val accountStatus = account?.accountStatus

        if (account != null) {
            accountNumber = account.accountNumber.toString()
        }

        heading.text = "Welcome $uname"


        openAccountButton.setOnClickListener {
            if (account == null) {
                val intent = Intent(this@HomeActivity, OpenAccountActivity::class.java)
                startActivity(intent)
            } else {
                AlertDialog.Builder(this).create().apply {
                    setTitle("Already Account is Created.")
                    setIcon(R.drawable.account_exists)
                    setMessage("You Already have an account you can't open one more account")
                    setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }

        transferMoneyButton.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                val intent = Intent(this@HomeActivity, TransferMoneyActivity::class.java)
                startActivity(intent)
            }
        }

        checkBalance.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                val intent = Intent(this@HomeActivity, AccountBalanceActivity::class.java)
                startActivity(intent)
            }
        }

        checkTransaction.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                val intent = Intent(this@HomeActivity, TransactionActivity::class.java)
                startActivity(intent)
            }
        }

        requestDebit.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                AlertDialog.Builder(this@HomeActivity).apply {
                    setTitle("Request For Debit Card")
                    setMessage("Are you sure you want to make a request for debit card with account number: $accountNumber ?")
                    setIcon(R.drawable.ic_info_black_24dp)
                    setPositiveButton("Yes") { dialog, _ ->
                        insertDebitRequest(accountNumber.toLong())
                        dialog.dismiss()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }

        requestCheque.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                AlertDialog.Builder(this@HomeActivity).apply {
                    setTitle("Request For Cheque Book")
                    setMessage("Are you sure you want to make a request for cheque book with account number: $accountNumber ?")
                    setIcon(R.drawable.ic_info_black_24dp)
                    setPositiveButton("Yes") { dialog, _ ->
                        insertChequeBookRequest(accountNumber.toLong())
                        dialog.dismiss()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }

        accountInfo.setOnClickListener {
            if (account == null) {
                showOpenAccountDialog()
            } else if (accountStatus != "active") {
                waitAccountStatusDialog()
            } else {
                val intent = Intent(this@HomeActivity, AccountInfoActivity::class.java)
                startActivity(intent)
            }
        }

        changePassword.setOnClickListener {
            val intent = Intent(this@HomeActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Logout From MoneyMate")
                setMessage("Are you sure you want to logout from the MoneyMate?")
                setIcon(R.drawable.ic_info_black_24dp)
                setPositiveButton("Yes") { _, _ ->
                    // val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    private fun insertDebitRequest(accountNumber: Long) {
        val dbHelper = RequestTableHelper(this@HomeActivity)
        val request = Request(
            accountNo = accountNumber,
            requestType = "debit-card"
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

    private fun insertChequeBookRequest(accountNumber: Long) {
        val dbHelper = RequestTableHelper(this@HomeActivity)
        val request = Request(
            accountNo = accountNumber,
            requestType = "cheque-book",
            requestStatus = "Pending",
            requestDate = ""
        )

        val success = dbHelper.insertRequest(request)

        if (success) {
            AlertDialog.Builder(this@HomeActivity).create().apply {
                setMessage("Your request for a cheque book has been successfully sent. You'll receive it soon.")
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

    private fun showOpenAccountDialog() {
        AlertDialog.Builder(this).create().apply {
            setTitle("Access Denied.")
            setIcon(R.drawable.error)
            setMessage("To use this services please open an account first.")
            setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun waitAccountStatusDialog() {
        AlertDialog.Builder(this).create().apply {
            setTitle("Access Denied.")
            setIcon(R.drawable.ic_info_black_24dp)
            setMessage("Please Wait till Your Account Is Not Activated")
            setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }


    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Exit MoneyMate")
            setMessage("Are you sure you want to exit?")
            setIcon(R.drawable.ic_info_black_24dp)
            setPositiveButton("Yes") { _, _ ->
                finishAffinity()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }
}
