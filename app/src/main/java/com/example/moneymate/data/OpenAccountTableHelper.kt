package com.example.moneymate.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.moneymate.model.Account

private const val DB_VERSION = 1
private const val DB_NAME = "MoneyMate_Database"
private const val TABLE_NAME = "accounts"

private const val COLUMN_ID = "account_no"
private const val COLUMN_UID = "uid"
private const val COLUMN_NAME = "name"
private const val COLUMN_GENDER = "gender"
private const val COLUMN_MOBILE = "mobile"
private const val COLUMN_EMAIL = "email"
private const val COLUMN_DOB = "dob"
private const val COLUMN_PAN = "pan"
private const val COLUMN_ADDRESS = "address"
private const val COLUMN_STATE = "state"
private const val COLUMN_CITY = "city"
private const val COLUMN_PIN = "pin"
private const val COLUMN_IFSC = "ifsc"
private const val COLUMN_BRANCH = "branch"
private const val COLUMN_NOMINEE = "nominee_name"
private const val COLUMN_NOMINEE_ACCOUNT_NO = "nominee_account_no"
private const val COLUMN_NOMINEE_ACCOUNT_TYPE = "nominee_account_type"
private const val COLUMN_ACCOUNT_OPEN = "account_open_date"
private const val COLUMN_ACCOUNT_STATUS = "account_status"

class OpenAccountTableHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null,DB_VERSION) {

    private val CREATE_TABLE = """
    CREATE TABLE IF NOT EXISTS $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY,
        $COLUMN_UID INTEGER,
        $COLUMN_NAME TEXT,
        $COLUMN_GENDER TEXT,
        $COLUMN_MOBILE TEXT,
        $COLUMN_EMAIL TEXT,
        $COLUMN_DOB TEXT,
        $COLUMN_PAN TEXT,
        $COLUMN_ADDRESS TEXT,
        $COLUMN_STATE TEXT,
        $COLUMN_CITY TEXT,
        $COLUMN_PIN TEXT,
        $COLUMN_IFSC TEXT,
        $COLUMN_BRANCH TEXT,
        $COLUMN_NOMINEE TEXT,
        $COLUMN_NOMINEE_ACCOUNT_NO TEXT,
        $COLUMN_NOMINEE_ACCOUNT_TYPE TEXT,
        $COLUMN_ACCOUNT_OPEN TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        $COLUMN_ACCOUNT_STATUS TEXT,
        Foreign Key ($COLUMN_UID) REFERENCES User(id)
    )
"""

    private val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME "

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun openAccounnt(account:Account):Boolean{
        val db = writableDatabase
        onCreate(db)
        val values = ContentValues().apply {
            put(COLUMN_NAME, account.name)
            put(COLUMN_UID, account.userid)
            put(COLUMN_ID, account.accountNumber)
            put(COLUMN_GENDER, account.gender)
            put(COLUMN_MOBILE, account.mobileNumber)
            put(COLUMN_EMAIL, account.email)
            put(COLUMN_DOB, account.dob)
            put(COLUMN_PAN, account.pan)
            put(COLUMN_ADDRESS, account.address)
            put(COLUMN_STATE, account.state)
            put(COLUMN_CITY, account.city)
            put(COLUMN_PIN, account.pincode)
            put(COLUMN_IFSC, account.ifsc)
            put(COLUMN_BRANCH, account.branch)
            put(COLUMN_NOMINEE, account.nomineeName)
            put(COLUMN_NOMINEE_ACCOUNT_NO, account.nomineeAccount)
            put(COLUMN_NOMINEE_ACCOUNT_TYPE, account.nomineeAccountType)
            put(COLUMN_ACCOUNT_STATUS, account.accountStatus)
        }
        Log.d("db-debug",values.toString())
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getAllAccounts(): List<Account> {
        val accountList = mutableListOf<Account>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val account = Account(
                    accountNumber = cursor.getLong(0),
                    userid = cursor.getInt(1),
                    name = cursor.getString(2),
                    gender = cursor.getString(3),
                    mobileNumber = cursor.getString(4),
                    email = cursor.getString(5),
                    dob = cursor.getString(6),
                    pan = cursor.getString(7),
                    address = cursor.getString(8),
                    state = cursor.getString(9),
                    city = cursor.getString(10),
                    pincode = cursor.getString(11),
                    ifsc = cursor.getString(12),
                    branch = cursor.getString(13),
                    nomineeName = cursor.getString(14),
                    nomineeAccount = cursor.getString(15),
                    nomineeAccountType = cursor.getString(16),
                    accountOpenDate = cursor.getString(17),
                    accountStatus = cursor.getString(18)
                )
                accountList.add(account)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return accountList
    }

}