package com.example.moneymate

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.example.moneymate.data.RegistrationTableHelper
import com.example.moneymate.model.User

class LoginActivity : AppCompatActivity() {

    private lateinit var uname: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupLink: TextView
    private lateinit var staffLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        uname = findViewById(R.id.unametxt)
        password = findViewById(R.id.passwordtxt)
        loginBtn = findViewById(R.id.loginbtn)
        signupLink = findViewById(R.id.signupLink)
        staffLoginLink = findViewById(R.id.staffLoginLink)


        signupLink.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        staffLoginLink.setOnClickListener{
            val intent = Intent(this@LoginActivity,StaffLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val enteredUname = uname.text.toString()
        val enteredPassword = password.text.toString()

        if (enteredUname.isEmpty()) {
            uname.error = "Username is required"
        }

        if (enteredPassword.isEmpty()) {
            password.error = "Password is required"
        }

        val dbHelper = RegistrationTableHelper(this)

        val userData = dbHelper.isValidUser(enteredUname, enteredPassword)


        if (userData != null) {

            saveUserInfo(userData)
            AlertDialog.Builder(this).create().apply {
                setTitle("Successful Login")
                setIcon(R.drawable.sucess)
                setMessage("You Logged in Successfully")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                    startActivity(intent)

                }
                show()
            }
        } else {
           AlertDialog.Builder(this).create().apply {
               setTitle("Check Credentials")
               setIcon(R.drawable.error)
               setMessage("Incorrect username or password")
               setButton(DialogInterface.BUTTON_POSITIVE,"OK"){ dialog,_->
                   dialog.dismiss()
               }
               show()
           }
        }
    }

    private fun saveUserInfo(userData: User) {
        val sharedPreference = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        editor.putInt("userId", userData.id!!)
        editor.putString("uname", userData.uname)
        editor.putString("email", userData.email)

        editor.apply()
    }
}
