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

        userDetails.setOnClickListener {
            val intent = Intent(this@StaffDashboardActivity,UserDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }

        addStaff.setOnClickListener {
            val intent = Intent(this@StaffDashboardActivity,AddStaffActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
