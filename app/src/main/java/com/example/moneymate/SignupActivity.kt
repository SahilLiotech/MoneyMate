package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.RegistrationTableHelper
import com.example.moneymate.model.User
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var uname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var cpassword: EditText
    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        uname = findViewById(R.id.unametxt)
        email = findViewById(R.id.emailtxt)
        password = findViewById(R.id.passwordtxt)
        cpassword = findViewById(R.id.cpasswordtxt)
        signupBtn = findViewById(R.id.signupbtn)

        signupBtn.setOnClickListener {
            if (validateInputs()) {
                addUser()
            }
        }

        loginLink.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addUser() {
        val username = uname.text.toString()
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        val dbHelper = RegistrationTableHelper(this)
        val userId = dbHelper.addUser(username, userEmail, userPassword)

        if (userId != -1L) {
            AlertDialog.Builder(this).create().apply {
                setTitle("Successful Registration")
                setIcon(R.drawable.sucess)
                setMessage("Your Registration Is Completed")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                show()
            }
        } else {
            AlertDialog.Builder(this).create().apply {
                setTitle("Registration Canceled")
                setIcon(R.drawable.error)
                setMessage("Your Registration Is Not Completed")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val username = uname.text.toString().trim()
        val userEmail = email.text.toString().trim()
        val userPassword = password.text.toString().trim()
        val confirmPassword = cpassword.text.toString().trim()

        fun isEmailValid(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
            return email.matches(emailRegex.toRegex())
        }

        if (username.isEmpty()) {
            uname.error = "Username is required."
            return false
        }

        if (userEmail.isEmpty()) {
            email.error = "Email is required."
            return false
        } else if (!isEmailValid(userEmail)) {
            email.error = "Enter a valid email address."
            return false
        }

        if (userPassword.isEmpty()) {
            password.error = "Password is required."
            return false
        } else if (userPassword.length < 6) {
            password.error = "Password must be at least 6 characters."
            return false
        }

        if (confirmPassword.isEmpty()) {
            cpassword.error = "Confirm Password is required."
            return false
        } else if (userPassword != confirmPassword) {
            cpassword.error = "Passwords do not match."
            return false
        }

        uname.error = null
        email.error = null
        password.error = null
        cpassword.error = null

        return true
    }
}
