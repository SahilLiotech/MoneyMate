package com.example.moneymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymate.adapters.AccountsAdapter

class ViewActiveAccountsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_active_accounts)

        val recyclerView: RecyclerView = findViewById(R.id.vaa_recycler_view)
        recyclerView.adapter = AccountsAdapter(this, "active")
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}