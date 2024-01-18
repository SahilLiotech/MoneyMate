package com.example.moneymate.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.moneymate.model.Transaction

private const val DB_VERSION = 1
private const val DB_NAME = "MoneyMate_Database"

internal const val COLUMN_ACCOUNT_NUM = "account_no"
private const val COLUMN_TRANSACTION_TYPE = "transaction_type"
private const val COLUMN_AMOUNT = "amount"
internal const val COLUMN_TOTAL_AMOUNT = "available_amount"
private const val COLUMN_DONE = "done_at"



class TransactionTableHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    companion object{
        internal const val TABLE_NAME = "transactions"
        internal const val COLUMN_ID = "tid"
    }

    private val CREATE_TABLE="""
        CREATE TABLE IF NOT EXISTS $TABLE_NAME(
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ACCOUNT_NUM INTEGER,
            $COLUMN_TRANSACTION_TYPE TEXT,
            $COLUMN_AMOUNT INTEGER,
            $COLUMN_TOTAL_AMOUNT INTEGER,
            $COLUMN_DONE TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

    //this method insert the transaction data into the database
    fun insertTransaction(transaction: Transaction):Long{
        val db = writableDatabase
        onCreate(db)
        val values=ContentValues().apply {
            put(COLUMN_ACCOUNT_NUM,transaction.accountNo)
            put(COLUMN_TRANSACTION_TYPE,transaction.transactionType)
            put(COLUMN_AMOUNT,transaction.amount)
            put(COLUMN_TOTAL_AMOUNT,transaction.totalAmount)
        }
        Log.d("debug-transaction",values.toString())
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

}