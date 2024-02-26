package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymate.adapters.AccountsAdapter

class DeactivatedAccountsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deactivated_accounts)
        val recyclerView: RecyclerView = findViewById(R.id.da_recycler_view)
        recyclerView.adapter = AccountsAdapter(this, "deactivate")
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}