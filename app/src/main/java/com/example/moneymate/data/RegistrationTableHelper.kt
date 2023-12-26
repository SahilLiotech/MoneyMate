package com.example.moneymate.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.moneymate.model.User

private const val DB_VERSION = 1
private const val DB_NAME = "MoneyMate_Database"
private const val TABLE_NAME = "User"

private const val COLUMN_ID = "id"
private const val COLUMN_UNAME = "uname"
private const val COLUMN_EMAIL = "email"
private const val COLUMN_PASSWORD = "password"
private const val COLUMN_REG_DATE = "registration_date"

class RegistrationTableHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null,DB_VERSION) {


    private val CREATE_TABLE = """
    CREATE TABLE IF NOT EXISTS $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_UNAME TEXT,
        $COLUMN_EMAIL TEXT,
        $COLUMN_PASSWORD TEXT,
        $COLUMN_REG_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
"""
    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //this function is used to add the data into the database
    fun addUser(uname: String, email: String, password: String): Long {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_UNAME, uname)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

//    //this function fetch the data based on the uname which is entered at the login time
//    fun getUserData(uname: String): User? {
//        val db = readableDatabase
//
//        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UNAME = ?"
//        val cursor = db.rawQuery(query, arrayOf(uname))
//
//        var user: User? = null
//
//        if (cursor.moveToFirst()) {
//            val id = cursor.getInt(0)
//            val username = cursor.getString(1)
//            val email = cursor.getString(2)
//            val password = cursor.getString(3)
//            val regDate = cursor.getString(4)
//
//            user = User(id, username, email, password, regDate)
//        }
//
//        cursor.close()
//        db.close()
//
//        return user
//    }

    //this function is use to fetch all the records from the database
    fun viewData(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val uname = cursor.getString(1)
                val email = cursor.getString(2)
                val password = cursor.getString(3)
                val regDate = cursor.getString(4)

                val user = User(id, uname, email, password, regDate)
                userList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }

    //this function check that entered uname and password in login activity is exist in the database or not
    fun isValidUser(uname: String, password: String): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(uname, password))

        var user: User? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(0)
            val username = cursor.getString(1)
            val email = cursor.getString(2)
            val userPassword = cursor.getString(3)
            val regDate = cursor.getString(4)

            user = User(id, username, email, userPassword, regDate)
        }

        cursor.close()
        db.close()
        Log.d("db-debug", user.toString())

        return user
    }

}