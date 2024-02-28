package com.example.moneymate

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.moneymate.data.OpenAccountTableHelper
import java.util.*
import kotlin.collections.ArrayList

class UpdateAccountActivity : AppCompatActivity() {

    private lateinit var accountNo: EditText
    private lateinit var name: EditText
    private lateinit var radioGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var mobileNo: EditText
    private lateinit var email: EditText
    private lateinit var dob: EditText
    private lateinit var panCard: EditText
    private lateinit var address: EditText
    private lateinit var state: EditText
    private lateinit var city: EditText
    private lateinit var pinCode: EditText
    private lateinit var updateButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_account)

        accountNo = findViewById(R.id.account_no)
        name = findViewById(R.id.update_name)
        radioGender = findViewById(R.id.radio_gender)
        radioMale = findViewById(R.id.update_radio_male)
        radioFemale = findViewById(R.id.update_radio_female)
        mobileNo = findViewById(R.id.update_mobile_no)
        email = findViewById(R.id.update_email)
        dob = findViewById(R.id.update_dob)
        panCard = findViewById(R.id.update_pan_card)
        address = findViewById(R.id.update_address)
        state = findViewById(R.id.update_state)
        city = findViewById(R.id.update_city)
        pinCode = findViewById(R.id.update_zip_code)
        updateButton = findViewById(R.id.submit_update_account_request)

        val getAccountNo = intent.getStringExtra("accountNumber")
        val getAddress = intent.getStringExtra("address")
        val getCity = intent.getStringExtra("city")
        val getState = intent.getStringExtra("state")
        val getPan = intent.getStringExtra("pan")
        val getPincode = intent.getStringExtra("pincode")
        val getName = intent.getStringExtra("name")
        val getGender = intent.getStringExtra("gender")
        val getMobile = intent.getStringExtra("mobileNumber")
        val getEmail = intent.getStringExtra("email")
        val getDob = intent.getStringExtra("dob")

        accountNo.setText(getAccountNo)
        name.setText(getName)
        when (getGender) {
            "Male" -> radioMale.isChecked = true
            "Female" -> radioFemale.isChecked = true
        }
        mobileNo.setText(getMobile)
        email.setText(getEmail)
        dob.setText(getDob)
        panCard.setText(getPan)
        address.setText(getAddress)
        state.setText(getState)
        city.setText(getCity)
        pinCode.setText(getPincode)

        val selectedGender = when (radioGender.checkedRadioButtonId) {
            R.id.update_radio_male -> "Male"
            R.id.update_radio_female -> "Female"
            else -> ""
        }

        accountNo.isClickable = false
        accountNo.isFocusableInTouchMode = false
//        accountNo.isFocusable = false

        dob.isFocusable = false
        dob.isFocusableInTouchMode = false
        dob.setOnClickListener {
            showDatePickerDialog()
        }

        updateButton.setOnClickListener {
            val dbHelper = OpenAccountTableHelper(this)
            val data = dbHelper.updateAccountDetails(
                accountNo.text.toString(),
                name.text.toString(),
                selectedGender,
                mobileNo.text.toString(),
                email.text.toString(),
                dob.text.toString(),
                panCard.text.toString(),
                address.text.toString(),
                state.text.toString(),
                city.text.toString(),
                pinCode.text.toString()
            )

            if (data > 0) {
                AlertDialog.Builder(this).create().apply {
                    setTitle("Account Update")
                    setMessage("Account Detail Of Account NO: $getAccountNo is updated")
                    setIcon(R.drawable.sucess)
                    setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ ->
                        val intent =
                            Intent(this@UpdateAccountActivity, StaffDashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    show()
                }

            } else {
                Toast.makeText(this, "Error updating records", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                dob.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}