package com.example.moneymate

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.RegistrationTableHelper
import com.example.moneymate.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var uname: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        uname = findViewById(R.id.unametxt)
        password = findViewById(R.id.passwordtxt)
        loginBtn = findViewById(R.id.loginbtn)

        val prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val content = prefs.all
        Log.d("content",content.toString())

        signupLink.setOnClickListener {
            val intent = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val enteredUname = uname.text.toString()
        val enteredPassword = password.text.toString()

        val dbHelper = RegistrationTableHelper(this)

        val userData = dbHelper.isValidUser(enteredUname, enteredPassword)

        if (userData != null) {

            // User is valid, save user data in SharedPreferences
            saveUserInfo(userData)
            // Log.d("db-debug", "user id: ${userData.id}")

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
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserInfo(userData: User) {
        val sharedPreference = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()

        editor.putInt("userId", userData.id)
        editor.putString("uname", userData.uname)
        editor.putString("email", userData.email)

        editor.commit()
    }
}
