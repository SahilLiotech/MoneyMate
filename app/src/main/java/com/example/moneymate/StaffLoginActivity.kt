package com.example.moneymate

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.StaffRegistrationTableHelper
import com.example.moneymate.model.Staff

class StaffLoginActivity : AppCompatActivity() {

    private lateinit var userLoginLink: TextView
    private lateinit var staffLoginLink: Button
    private lateinit var userName: EditText
    private lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_login)

        userLoginLink = findViewById(R.id.userLoginLink)
        staffLoginLink = findViewById(R.id.staffloginbtn)
        userName = findViewById(R.id.unametxt)
        password = findViewById(R.id.passwordtxt)


        staffLoginLink.setOnClickListener {
            staffLogin()
        }

        userLoginLink.setOnClickListener {
            val intent = Intent(this@StaffLoginActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun staffLogin() {
        val enteredUname = userName.text.toString()
        val enteredPassword = password.text.toString()

        if (enteredUname.isEmpty()) {
            userName.error = "Username is required"
        }

        if (enteredPassword.isEmpty()) {
            password.error = "Password is required"
        }

        val dbHelper = StaffRegistrationTableHelper(this)

        val userData = dbHelper.isValidStaff(enteredUname, enteredPassword)

        if (userData != null) {

            saveUserInfo(userData)
            AlertDialog.Builder(this).create().apply {
                setTitle("Successful Login")
                setIcon(R.drawable.sucess)
                setMessage("You Logged in Successfully")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@StaffLoginActivity, StaffDashboardActivity::class.java)
                    startActivity(intent)
                }
                show()
            }
        } else {
            AlertDialog.Builder(this).create().apply {
                setTitle("Check Credentials")
                setIcon(R.drawable.error)
                setMessage("Incorrect username or password")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }

    }

    private fun saveUserInfo(staffData: Staff) {
        val sharedPreference = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        editor.putInt("staffId", staffData.id)
        editor.putString("staffName", staffData.uname)
        editor.putString("staffEmail", staffData.email)

        editor.commit()
    }
}
