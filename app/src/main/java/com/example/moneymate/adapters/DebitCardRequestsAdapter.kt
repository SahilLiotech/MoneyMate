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
import com.example.moneymate.data.RequestTableHelper
import com.example.moneymate.model.Request

internal class DebitCardRequestsAdapter(private val context: Context) : RecyclerView.Adapter<DebitCardRequestsAdapter.ViewHolder>() {
    private var debitCardRequests: ArrayList<Request>
    init {
        val helper = RequestTableHelper(context)
        debitCardRequests = helper.getAllRequestsOf("debit-card")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context!!)
        val myView = inflater.inflate(R.layout.debit_card_requests_row, parent, false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int = debitCardRequests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.request = debitCardRequests[position]
        holder.setDetails()
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var request: Request = Request()

        fun setDetails() {
            val reqIdTextView: TextView = itemView.findViewById(R.id.dcr_request_id_textView)
            val accountIdTextView: TextView = itemView.findViewById(R.id.dcr_account_id_textView)
            val approveButton: Button = itemView.findViewById(R.id.dcr_approve_btn)
            val rejectButton: Button = itemView.findViewById(R.id.dcr_reject_btn)

            var txt = String.format(context.resources.getString(R.string.request_id), request.requestId.toString())
            reqIdTextView.text = txt

            txt = String.format(context.resources.getString(R.string.account_id), request.accountNo.toString())
            accountIdTextView.text = txt

            approveButton.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle("Approve")
                    setMessage("Are you sure you want to approve the request of Account ID: ${request.accountNo}")
                    setPositiveButton("Yes") { _, _ ->
                    }

                    setNegativeButton("No", null)
                    setCancelable(false)
                }.show()
            }
        }
    }
}