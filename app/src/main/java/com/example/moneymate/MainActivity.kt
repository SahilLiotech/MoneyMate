package com.example.moneymate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.moneymate.data.RegistrationTableHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayAllUsers()

        val handler = Handler()
        handler.postDelayed({
            if (isUserLoggedIn()) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@MainActivity, SignupActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 3000)

    }

    private fun displayAllUsers() {
        val dbHelper = RegistrationTableHelper(this)
        val userList = dbHelper.viewData()

        for (user in userList) {
            Log.d("UserRecord", user.toString())
        }
    }

    fun isUserLoggedIn():Boolean{
        val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val uname = sharedPreferences.getString("uname", "user")

        return uname != "user"
    }

}
