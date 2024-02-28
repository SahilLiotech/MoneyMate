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
internal const val COLUMN_RECEIVER_ACCOUNT_NUM = "receiver_account_no"
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
            $COLUMN_ACCOUNT_NUM INTEGER NOT NULL,
            $COLUMN_RECEIVER_ACCOUNT_NUM INTEGER NOT NULL,
            $COLUMN_TRANSACTION_TYPE TEXT NOT NULL,
            $COLUMN_AMOUNT INTEGER NOT NULL,
            $COLUMN_TOTAL_AMOUNT INTEGER NOT NULL DEFAULT 0,
            $COLUMN_DONE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
            Foreign Key ($COLUMN_ACCOUNT_NUM) REFERENCES Account(accountNumber)
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
            put(COLUMN_RECEIVER_ACCOUNT_NUM,transaction.receiverAccountNo)
            put(COLUMN_TRANSACTION_TYPE,transaction.transactionType)
            put(COLUMN_AMOUNT,transaction.amount)
            put(COLUMN_TOTAL_AMOUNT,transaction.totalAmount)
        }
        Log.d("debug-transaction",values.toString())
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getTransactionDetail(accountNum:Long): List<Transaction>{
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ACCOUNT_NUM = ?"
        val db = readableDatabase
        onCreate(db)
        val cursor = db.rawQuery(query, arrayOf(accountNum.toString()))
        val transactions = mutableListOf<Transaction>()

        while (cursor.moveToNext()) {
            transactions.add(
                Transaction(
                    accountNo = cursor.getLong(1),
                    receiverAccountNo = cursor.getLong(2),
                    transactionType = cursor.getString(3),
                    amount = cursor.getInt(4),
                    totalAmount = cursor.getInt(5),
                    doneAt = cursor.getString(6)
                )
            )
        }
        Log.d("user-debug",transactions.toString())
        cursor.close()
        db.close()
        return transactions
    }

    fun getTotalAmount(accountNo: Long): Int {
        val db = this.readableDatabase
        var totalAmount = 0

        val query = "SELECT $COLUMN_TOTAL_AMOUNT FROM $TABLE_NAME WHERE $COLUMN_ACCOUNT_NUM = ?"
        val cursor = db.rawQuery(query, arrayOf(accountNo.toString()))

        if (cursor.moveToFirst()) {
            totalAmount = cursor.getInt(0)
        }

        cursor.close()
        db.close()

        return totalAmount
    }


}
