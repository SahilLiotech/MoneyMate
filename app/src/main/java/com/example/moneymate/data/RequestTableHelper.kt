package com.example.moneymate.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.moneymate.model.Request

private const val DB_VERSION = 1
private const val DB_NAME = "MoneyMate_Database"
private const val REQUEST_TABLE_NAME = "Request"

private const val COLUMN_REQUEST_ID = "requestId"
private const val COLUMN_ACCOUNT_NO = "account_no"
private const val COLUMN_REQUEST_TYPE = "request_type"
private const val COLUMN_REQUEST_DATE = "request_date"
private const val COLUMN_REQUEST_STATUS = "request_status"

class RequestTableHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    private val CREATE_TABLE = """
        CREATE TABLE IF NOT EXISTS $REQUEST_TABLE_NAME(
            $COLUMN_REQUEST_ID INTEGER PRIMARY KEY,
            $COLUMN_ACCOUNT_NO INTEGER NOT NULL,
            $COLUMN_REQUEST_TYPE TEXT NOT NULL,
            $COLUMN_REQUEST_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
            $COLUMN_REQUEST_STATUS TEXT NOT NULL DEFAULT 'unhandled',
            FOREIGN KEY ($COLUMN_ACCOUNT_NO) REFERENCES Account(accountNumber)
        )
    """

    private val DROP_TABLE = "DROP TABLE IF EXISTS $REQUEST_TABLE_NAME "

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun insertRequest(request:Request):Boolean{
        val db = writableDatabase
        onCreate(db)
        val values = ContentValues().apply {
            put(COLUMN_ACCOUNT_NO,request.accountNo)
            put(COLUMN_REQUEST_TYPE,request.requestType)
            put(COLUMN_REQUEST_STATUS,request.requestStatus)
        }
        val result = db.insert(REQUEST_TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

   /* fun getRequest():List<Request>{

        val requestList = mutableListOf<Request>()
        val query = "SELECT * FROM $REQUEST_TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val request = Request(
                   requestId = cursor.getInt(0),
                   accountNo = cursor.getLong(1),
                   requestType = cursor.getString(2),
                   requestDate = cursor.getString(3),
                   requestStatus = cursor.getString(4)
                )
              requestList.add(request)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return requestList
    }*/

    /**
     * This method is used to get all of the unhandled request of specific request type from the 'requests' table.
     * There is no validation implemented yet for checking whether the 'requests' table has any
     * corresponding records or not.
     *
     * The request type can either be:
     *  1. debit-card request
     *  2. cheque-book request
     *
     * @param requestType type of request that has to be fetched from the table.
     * @return list of unhandled requests.
     */
    fun getAllRequestsOf(requestType: String): ArrayList<Request> {
        val db = this.readableDatabase
        val requestList: ArrayList<Request> = ArrayList()
        val query = "SELECT * FROM $REQUEST_TABLE_NAME WHERE $COLUMN_REQUEST_STATUS = ? AND $COLUMN_REQUEST_TYPE = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf("unhandled", requestType))
        while (cursor.moveToNext()) {
            val request = Request(
                requestId = cursor.getInt(0),
                accountNo = cursor.getLong(1),
                requestType = cursor.getString(2),
                requestDate = cursor.getString(3)
            )
            requestList.add(request)
        }

        cursor.close()
        return requestList
    }
}
