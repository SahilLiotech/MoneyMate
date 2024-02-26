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
            $COLUMN_REQUEST_STATUS TEXT NOT NULL DEFAULT 'Pending',
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
        }
        val result = db.insert(REQUEST_TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    /**
     * This method is used to get all the requests from the 'requests' table.
     * No matter what the status of the request is, this always returns list of requests.
     * Usually for debugging.
     *
     * @return list of requests.
     */
    fun getAllRequests(): ArrayList<Request> {
        val requestList = ArrayList<Request>()
        val query = "SELECT * FROM $REQUEST_TABLE_NAME"
        val db = readableDatabase
        onCreate(db)
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val request = Request(
                requestId = cursor.getInt(0),
                accountNo = cursor.getLong(1),
                requestType = cursor.getString(2),
                requestDate = cursor.getString(3),
                requestStatus = cursor.getString(4)
            )
            requestList.add(request)
        }

        cursor.close()
        return requestList
    }

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
        val cursor: Cursor = db.rawQuery(query, arrayOf("Pending", requestType))
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

    /**
     * This method is used to update the status of the request based on its ID and request type.
     *
     * @param requestId ID of the request.
     * @param requestType Type of the request.
     * @param status status of the request.
     */
    fun updateRequestStatus(requestId: String, requestType: String, status: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_REQUEST_STATUS, status)

        db.update(REQUEST_TABLE_NAME, values, "$COLUMN_REQUEST_ID=? AND $COLUMN_REQUEST_TYPE=?", arrayOf(requestId, requestType))
    }
}
