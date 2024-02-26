package com.example.moneymate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class StaffDashboardActivity : AppCompatActivity() {

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
    private lateinit var addStaff:LinearLayout
    private lateinit var logout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_dashboard)
        userDetails = findViewById(R.id.userdetail)
        addStaff = findViewById(R.id.addstaff)
        logout = findViewById(R.id.logout)
        debitRequest = findViewById(R.id.debitrequest)
        chequeRequest =findViewById(R.id.chequerequest)
        accountDetails = findViewById(R.id.bankaccountdetail)
        accountRequest = findViewById(R.id.bankrequest)
        deactivatedAccount = findViewById(R.id.deactivatedaccount)

        userDetails.setOnClickListener {
            intent = Intent(this@StaffDashboardActivity,UserDetailsActivity::class.java)
            startActivity(intent)
        }

        addStaff.setOnClickListener {
            intent = Intent(this@StaffDashboardActivity,AddStaffActivity::class.java)
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

        chequeRequest.setOnClickListener {
            intent = Intent(this, ChequeBookRequestsActivity::class.java)
            startActivity(intent)
        }

        deactivatedAccount.setOnClickListener {
            intent = Intent(this, DeactivatedAccountsActivity::class.java)
            startActivity(intent)
        }

        debitRequest.setOnClickListener {
            intent = Intent(this@StaffDashboardActivity, DebitCardRequestsActivity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
