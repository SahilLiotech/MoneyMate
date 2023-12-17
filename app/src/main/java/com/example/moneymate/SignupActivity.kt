package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.RegistrationTableHelper
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var uname:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var signupBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        uname=findViewById(R.id.unametxt)
        email=findViewById(R.id.emailtxt)
        password=findViewById(R.id.passwordtxt)
        signupBtn=findViewById(R.id.signupbtn)

        signupBtn.setOnClickListener {
            addUser()
        }

        loginLink.setOnClickListener {
            val intent = Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun addUser(){
        val username = uname.text.toString()
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        val dbHelper = RegistrationTableHelper(this)
        val isRegistered = dbHelper.addUser(username, userEmail, userPassword)

        if (isRegistered) {
            AlertDialog.Builder(this).create().apply {
                setTitle("Successful Registration")
                setIcon(R.drawable.sucess)
                setMessage("Your Registration Is Completed")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                    val intent = Intent(this@SignupActivity,LoginActivity::class.java)
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

}
