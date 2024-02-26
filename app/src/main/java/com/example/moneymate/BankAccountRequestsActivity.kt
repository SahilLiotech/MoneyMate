package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymate.adapters.AccountsAdapter

class BankAccountRequestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_account_requests)
        val recyclerView: RecyclerView = findViewById(R.id.ba_recycler_view)
        recyclerView.adapter = AccountsAdapter(this, "pending")
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}