package com.example.moneymate.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymate.R
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.model.Account

internal class AccountsAdapter(private val context: Context, private val status: String) :
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {
    private var accounts: ArrayList<Account>

    init {
        val helper = OpenAccountTableHelper(context)
        accounts = helper.getAccountListOf(status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val myView = inflater.inflate(R.layout.account_row, parent, false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int = accounts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.account = accounts[position]
        holder.setDetails()
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var account = Account()

        fun setDetails() {
            val accountNo: TextView = itemView.findViewById(R.id.ar_account_no)
            accountNo.text = account.accountNumber.toString()
            val username: TextView = itemView.findViewById(R.id.ar_username)
            username.text = account.email
            val actionButton: Button = itemView.findViewById(R.id.ar_action_btn)
            val action: String
            actionButton.text = when (status) {
                "active" -> {
                    action = "deactivate"
                    action
                }
                "pending" -> {
                    action = "active"
                    action
                }
                else -> {
                    action = "reactivate"
                    action
                }
            }

            actionButton.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle(action)
                    setMessage("Are you sure you want to $action this account?")
                    setPositiveButton("Yes") { _, _ ->
                        val helper = OpenAccountTableHelper(context)
                        helper.updateAccountStatus(account.accountNumber.toString(), if (status == "deactivate") "active" else action)
                        accounts.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                    }
                    setNegativeButton("No", null)
                    setCancelable(false)

                    show()
                }
            }

        }
    }
}