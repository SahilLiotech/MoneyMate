package com.example.moneymate.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlin.Triple
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
private const val COLUMN_ACCOUNT_TYPE = "account_type"
private const val COLUMN_ACCOUNT_OPEN = "account_open_date"
private const val COLUMN_ACCOUNT_STATUS = "account_status"

class OpenAccountTableHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null,DB_VERSION) {

    private val CREATE_TABLE = """
    CREATE TABLE IF NOT EXISTS $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY,
        $COLUMN_UID INTEGER NOT NULL,
        $COLUMN_NAME TEXT NOT NULL,
        $COLUMN_GENDER TEXT NOT NULL,
        $COLUMN_MOBILE TEXT NOT NULL,
        $COLUMN_EMAIL TEXT NOT NULL,
        $COLUMN_DOB TEXT NOT NULL,
        $COLUMN_PAN TEXT NOT NULL,
        $COLUMN_ADDRESS TEXT NOT NULL,
        $COLUMN_STATE TEXT NOT NULL,
        $COLUMN_CITY TEXT NOT NULL,
        $COLUMN_PIN TEXT NOT NULL,
        $COLUMN_IFSC TEXT NOT NULL,
        $COLUMN_BRANCH TEXT NOT NULL,
        $COLUMN_ACCOUNT_TYPE TEXT NOT NULL,
        $COLUMN_ACCOUNT_OPEN TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        $COLUMN_ACCOUNT_STATUS TEXT NOT NULL,
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

    //this method insert the open account form data into the database
    fun openAccounnt(account:Account):Long {
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
            put(COLUMN_ACCOUNT_TYPE, account.accountType)
            put(COLUMN_ACCOUNT_STATUS, account.accountStatus)
        }
        Log.d("db-debug",values.toString())
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    //this method fetch the account detail based on the user id
    fun getAccountByUserId(userId: Int): Account?{
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UID = ?"
        val db = readableDatabase
        onCreate(db)
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        var account: Account? = null

        if (cursor.moveToFirst()) {
            account = Account(
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
                accountType = cursor.getString(14),
                accountOpenDate = cursor.getString(15),
                accountStatus = cursor.getString(16)
            )
        }
        cursor.close()
        return account
    }

    //this function is used to get the account info(account name, account number and account balance) of user according to user id
    fun getAccountDetailsByUserId(userId: Int): Triple<Long, String, Int>? {
        val query = """
        SELECT A.$COLUMN_ID, A.$COLUMN_NAME, T.$COLUMN_TOTAL_AMOUNT
        FROM $TABLE_NAME A
        LEFT JOIN ${TransactionTableHelper.TABLE_NAME} T ON A.$COLUMN_ID = T.$COLUMN_ACCOUNT_NUM
        WHERE A.$COLUMN_UID = ?
    """

        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        var accountDetails: Triple<Long, String, Int>? = null

        if (cursor.moveToFirst()) {
            val accountNumber = cursor.getLong(0)
            val accountName = cursor.getString(1)
            val totalAmount = cursor.getInt(2)
            accountDetails = Triple(accountNumber, accountName, totalAmount)
        }

        cursor.close()
        return accountDetails
    }

    fun getAccountList(): ArrayList<Account> {
        val db = this.readableDatabase
        val accounts: ArrayList<Account> = ArrayList()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        while (cursor.moveToNext()) {
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
                accountType = cursor.getString(14),
                accountOpenDate = cursor.getString(15),
                accountStatus = cursor.getString(16)
            )

            accounts.add(account)
        }
        cursor.close()

        return accounts
    }

    fun getAccountListOf(type: String): ArrayList<Account> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ACCOUNT_STATUS = ?",
            arrayOf(type)
        )
        val accountList: ArrayList<Account> = ArrayList()

        while (cursor.moveToNext()) {
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
                accountType = cursor.getString(14),
                accountOpenDate = cursor.getString(15),
                accountStatus = cursor.getString(16)
            )

            accountList.add(account)
        }

        cursor.close()
        return accountList
    }

    fun updateAccountStatus(id: String, status: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACCOUNT_STATUS, status)
        }

        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id))
    }
}