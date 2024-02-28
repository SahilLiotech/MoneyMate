package com.example.moneymate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneymate.data.RegistrationTableHelper

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var oldpwd: EditText
    private lateinit var newpwd: EditText
    private lateinit var confirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        oldpwd = findViewById(R.id.old_password)
        newpwd = findViewById(R.id.new_password)
        confirm = findViewById(R.id.change_password_btn)

        val pref = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userId = pref.getInt("userId", -1)

        confirm.setOnClickListener {
            val oldPassword = oldpwd.text.toString()
            val newPassword = newpwd.text.toString()

            val dbHelper = RegistrationTableHelper(this)
            dbHelper.isOldPasswordCorrect(oldPassword = oldPassword, uid = userId.toString())
            val matchedOldPassword = dbHelper.isOldPasswordCorrect(userId.toString(), oldPassword)
            if (matchedOldPassword) {
                val updatedPassword = dbHelper.updateUserPassword(userId.toString(), newPassword)
                if (updatedPassword > 0) {
                    Toast.makeText(this, "Your Password is Changed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Your Password is Not Changed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Correct Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}