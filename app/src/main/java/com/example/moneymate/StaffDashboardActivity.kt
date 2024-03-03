package com.example.moneymate

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.data.RegistrationTableHelper
import com.example.moneymate.data.TransactionTableHelper
import com.example.moneymate.model.Account

class StaffDashboardActivity : AppCompatActivity() {

    private lateinit var staffName: TextView
    private lateinit var userDetails: LinearLayout
    private lateinit var accountDetails: LinearLayout
    private lateinit var updateAccount: LinearLayout
    private lateinit var accountRequest: LinearLayout
    private lateinit var debitRequest: LinearLayout
    private lateinit var chequeRequest: LinearLayout
    private lateinit var withdrawMoney: LinearLayout
    private lateinit var depositMoney: LinearLayout
    private lateinit var changePassword: LinearLayout
    private lateinit var deactivatedAccount: LinearLayout
    private lateinit var addStaff: LinearLayout
    private lateinit var logout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_dashboard)
        userDetails = findViewById(R.id.userdetail)
        addStaff = findViewById(R.id.addstaff)
        logout = findViewById(R.id.logout)
        debitRequest = findViewById(R.id.debitrequest)
        chequeRequest = findViewById(R.id.chequerequest)
        accountDetails = findViewById(R.id.bankaccountdetail)
        accountRequest = findViewById(R.id.bankrequest)
        deactivatedAccount = findViewById(R.id.deactivatedaccount)
        staffName = findViewById(R.id.staffname)
        changePassword = findViewById(R.id.changepasswordstaff)
        updateAccount = findViewById(R.id.updatebankaccount)
        depositMoney = findViewById(R.id.depositmoney)
        withdrawMoney = findViewById(R.id.withdrawmoney)

        val pref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val staffUserName = pref.getString("staffName", "user")

/*
        val helper = TransactionTableHelper(this)
        for (record in helper.getAllTransactions()) {
            Log.d("db-debug", record.toString())
        }

        val userHelper = RegistrationTableHelper(this)
        for (record in userHelper.viewData()) {
            Log.d("db-debug", record.toString())
        }
*/

        val accountTableHelper = OpenAccountTableHelper(this)
        for (record in accountTableHelper.getAccountList()) {
            Log.d("db-debug", record.toString())
        }

        staffName.text = staffUserName

        userDetails.setOnClickListener {
            intent = Intent(this@StaffDashboardActivity, UserDetailsActivity::class.java)
            startActivity(intent)
        }

        addStaff.setOnClickListener {
            intent = Intent(this@StaffDashboardActivity, AddStaffActivity::class.java)
            startActivity(intent)
        }

        accountDetails.setOnClickListener {
            intent = Intent(this, ViewActiveAccountsActivity::class.java)
            startActivity(intent)
        }

        accountRequest.setOnClickListener {
            intent = Intent(this, BankAccountRequestsActivity::class.java)
            startActivity(intent)
        }

        changePassword.setOnClickListener {
            intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        chequeRequest.setOnClickListener {
            intent = Intent(this, ChequeBookRequestsActivity::class.java)
            startActivity(intent)
        }

        deactivatedAccount.setOnClickListener {
            intent = Intent(this, DeactivatedAccountsActivity::class.java)
            startActivity(intent)
        }

        debitRequest.setOnClickListener {
            intent = Intent(this, DebitCardRequestsActivity::class.java)
            startActivity(intent)
        }

        withdrawMoney.setOnClickListener {
            intent = Intent(this,WithdrawActivity::class.java)
            startActivity(intent)
        }

        depositMoney.setOnClickListener {
            intent = Intent(this,DepositeActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Logout From MoneyMate Staff")
                setMessage("Are you sure you want to logout from the MoneyMate Staff?")
                setIcon(R.drawable.ic_info_black_24dp)
                setPositiveButton("Yes") { _, _ ->
                    val editor: SharedPreferences.Editor = pref.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(this@StaffDashboardActivity, StaffLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }

        updateAccount.setOnClickListener {
            AlertDialog.Builder(this).apply {
                val layoutManager = LinearLayout(this@StaffDashboardActivity)
                layoutManager.setPadding(20, 10, 20, 0)
                layoutManager.orientation = LinearLayout.VERTICAL
                val editText = EditText(this@StaffDashboardActivity)
                editText.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                editText.hint = "Account No"
                layoutManager.addView(editText)
                setView(layoutManager)

                setPositiveButton("Ok") { _, _ ->
                    val helper = OpenAccountTableHelper(this@StaffDashboardActivity)
                    val account: Account? = helper.getAccountDetailsById(editText.text.toString())

                    if (account == null) {
                        Toast.makeText(
                            this@StaffDashboardActivity,
                            "Invalid account number",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@StaffDashboardActivity,
                            "Found an account",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("db-debug", account.toString())
                        intent =
                            Intent(this@StaffDashboardActivity, UpdateAccountActivity::class.java)
                        intent.putExtra("accountNumber", account.accountNumber.toString())
                        intent.putExtra("address", account.address)
                        intent.putExtra("city", account.city)
                        intent.putExtra("state", account.state)
                        intent.putExtra("pan", account.pan)
                        intent.putExtra("pincode", account.pincode)
                        intent.putExtra("name", account.name)
                        intent.putExtra("gender", account.gender)
                        intent.putExtra("mobileNumber", account.mobileNumber)
                        intent.putExtra("email", account.email)
                        intent.putExtra("dob", account.dob)

                        startActivity(intent)
                    }
                }
                setNegativeButton("Cancel", null)
                setCancelable(false)
                create()
                show()
            }
        }
    }
}
