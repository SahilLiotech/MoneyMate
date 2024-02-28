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

internal class RequestsAdapter(private val context: Context, private val requestType: String) :
    RecyclerView.Adapter<RequestsAdapter.ViewHolder>() {
    private var requests: ArrayList<Request>

    init {
        val helper = RequestTableHelper(context)
        requests = helper.getAllRequestsOf(requestType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context!!)
        val myView = inflater.inflate(R.layout.debit_card_requests_row, parent, false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int = requests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.request = requests[position]
        holder.setDetails()
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var request: Request = Request()

        fun setDetails() {
            val reqIdTextView: TextView = itemView.findViewById(R.id.dcr_request_id_textView)
            val accountIdTextView: TextView = itemView.findViewById(R.id.dcr_account_id_textView)
            val approveButton: Button = itemView.findViewById(R.id.dcr_approve_btn)
            val rejectButton: Button = itemView.findViewById(R.id.dcr_reject_btn)

            var txt = String.format(
                context.resources.getString(R.string.request_id),
                request.requestId.toString()
            )
            reqIdTextView.text = txt

            txt = String.format(
                context.resources.getString(R.string.account_id),
                request.accountNo.toString()
            )
            accountIdTextView.text = txt

            approveButton.setOnClickListener {
                showDialog(
                    "Approve",
                    "Are you sure you want to approve the request of ${request.accountNo}",
                    "Approved"
                )
            }

            rejectButton.setOnClickListener {
                showDialog(
                    "Reject",
                    "Are you sure you want to reject the request of ${request.accountNo}",
                    "Rejected"
                )
            }
        }

        private fun showDialog(title: String, msg: String, status: String) {
            AlertDialog.Builder(context).apply {
                setTitle(title)
                setMessage(msg)
                setPositiveButton("Yes") { _, _ ->
                    val helper = RequestTableHelper(context)
                    helper.updateRequestStatus(request.requestId.toString(), requestType, status)
                    requests.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }

                setNegativeButton("No", null)
                setCancelable(false)
            }.show()
        }
    }
}