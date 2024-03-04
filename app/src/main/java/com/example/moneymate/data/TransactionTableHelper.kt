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
private const val COLUMN_DONE = "done_at"


class TransactionTableHelper(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        internal const val TABLE_NAME = "transactions"
        internal const val COLUMN_ID = "tid"
    }

    private val CREATE_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME(
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_ACCOUNT_NUM INTEGER NOT NULL,
            $COLUMN_RECEIVER_ACCOUNT_NUM INTEGER NOT NULL,
            $COLUMN_TRANSACTION_TYPE TEXT NOT NULL,
            $COLUMN_AMOUNT INTEGER NOT NULL,
            $COLUMN_DONE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
            Foreign Key ($COLUMN_ACCOUNT_NUM) REFERENCES Account(accountNumber)
        )
    """

    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME "

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun getTransactionDetail(accountNum: Long): List<Transaction> {
        val query = """
            SELECT $COLUMN_ID, $COLUMN_TRANSACTION_TYPE, $COLUMN_AMOUNT, $COLUMN_DONE
                FROM $TABLE_NAME WHERE $COLUMN_ACCOUNT_NUM = ? OR $COLUMN_RECEIVER_ACCOUNT_NUM =?
                """
        val db = readableDatabase
        onCreate(db)
        val cursor = db.rawQuery(query, arrayOf(accountNum.toString(), accountNum.toString()))
        val transactions = mutableListOf<Transaction>()

        while (cursor.moveToNext()) {
            transactions.add(
                Transaction(
                    accountNo = cursor.getLong(0),
                    transactionType = cursor.getString(1),
                    amount = cursor.getInt(2),
                    doneAt = cursor.getString(3)
                )
            )
        }
        Log.d("user-debug", transactions.toString())
        cursor.close()
        db.close()
        return transactions
    }

    fun addTransaction(transaction: Transaction): Pair<String, Boolean> {
        val db = this.writableDatabase
        onCreate(db)
        val helper = OpenAccountTableHelper(context)

        if (transaction.transactionType == "transfer") {
            if (helper.getAccountDetailsById(transaction.accountNo.toString()) == null) {
                return Pair("Invalid sender account number", false)
            }

            if (helper.getAmountOf(transaction.accountNo.toString())!! < transaction.amount!!) {
                return Pair("Insufficient Amount", false)
            }
        }

        val recvAmt = helper.getAmountOf(transaction.receiverAccountNo.toString())
            ?: return Pair("Invalid receiver account number", false)
        when (transaction.transactionType) {
            "deposit" -> helper.updateAmountOf(
                transaction.receiverAccountNo.toString(),
                recvAmt + transaction.amount!!
            )
            "withdraw" -> {
                if (transaction.amount!! > recvAmt) return Pair("Insufficient Balance", false)
                helper.updateAmountOf(
                    transaction.receiverAccountNo.toString(),
                    recvAmt - transaction.amount!!
                )
            }
            "transfer" -> {
                helper.updateAmountOf(
                    transaction.accountNo.toString(),
                    recvAmt - transaction.amount!!
                )
            }
        }
        val values = ContentValues().apply {
            put(COLUMN_ACCOUNT_NUM, transaction.accountNo)
            put(COLUMN_RECEIVER_ACCOUNT_NUM, transaction.receiverAccountNo)
            put(COLUMN_TRANSACTION_TYPE, transaction.transactionType)
            put(COLUMN_AMOUNT, transaction.amount)
        }
        db.insert(TABLE_NAME, null, values)

        return Pair("transaction completed", true)
    }

    fun getAllTransactions(): ArrayList<Transaction> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        onCreate(db)
        val cursor = db.rawQuery(query, null)
        val transactions = ArrayList<Transaction>()

        while (cursor.moveToNext()) {
            transactions.add(
                Transaction(
                    accountNo = cursor.getLong(1),
                    receiverAccountNo = cursor.getLong(2),
                    transactionType = cursor.getString(3),
                    amount = cursor.getInt(4),
                    doneAt = cursor.getString(5)
                )
            )
        }
        cursor.close()
        return transactions
    }
}
