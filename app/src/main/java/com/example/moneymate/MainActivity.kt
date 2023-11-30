package com.example.moneymate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.moneymate.data.OpenAccountTableHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val helper = OpenAccountTableHelper(this)
//
//        for (account in helper.getAllAccounts()){
//            Log.d("db-helper",account.toString())
//        }

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

    fun isUserLoggedIn():Boolean{
        val sharedPreferences = getSharedPreferences("MoneyMate.Login", Context.MODE_PRIVATE)
        val uname = sharedPreferences.getString("uname", "user")

        return uname != "user"
    }

}
