package com.example.moneymate.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymate.R
import com.example.moneymate.data.RegistrationTableHelper
import com.example.moneymate.model.User

internal class UserDetailAdapter(val context:Context):RecyclerView.Adapter<UserDetailAdapter.ViewHolder>(){
    private var usersList: List<User>
    init {
         val dbHelper = RegistrationTableHelper(context)
         usersList = dbHelper.viewData()
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var user: User
        init {
            val btn: Button = itemView.findViewById(R.id.ud_delete_btn)

            btn.setOnClickListener {
                val res = RegistrationTableHelper(context).deleteUserById(user.id.toString())
                if (res > 0) {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Delete ${user.uname} ?")
                    builder.setMessage("Are you sure you want to Delete this user's account?")
                    builder.setPositiveButton("Yes") { _, _ ->
                        Log.d("alert-dbg", adapterPosition.toString())
                    }
                    builder.setNegativeButton("No", null)

                    builder.create().show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val myView = inflater.inflate(R.layout.user_row,parent,false)

        return ViewHolder(myView)
    }

    override fun getItemCount(): Int {
       return  usersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]
        holder.user = user
    }
}