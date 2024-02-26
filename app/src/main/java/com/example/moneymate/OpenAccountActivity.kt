package com.example.moneymate

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.model.Account
import java.security.SecureRandom
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class OpenAccountActivity : AppCompatActivity() {
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
    private lateinit var accountType: Spinner
    private lateinit var submitButton: Button
    private lateinit var accountTypeList: ArrayList<String>
    private lateinit var prefs: SharedPreferences
    private var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_account)
        prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        userId = prefs.getInt("userId", -1)
        accountTypeList = ArrayList()
        populateAccountTypeList()

        name = findViewById(R.id.name)
        radioGender = findViewById(R.id.radio_gender)
        radioMale = findViewById(R.id.radio_male)
        radioFemale = findViewById(R.id.radio_female)
        mobileNo = findViewById(R.id.mobile_no)
        email = findViewById(R.id.email)
        dob = findViewById(R.id.dob)
        panCard = findViewById(R.id.pan_card)
        address = findViewById(R.id.address)
        state = findViewById(R.id.state)
        city = findViewById(R.id.city)
        pinCode = findViewById(R.id.zip_code)
        accountType = findViewById(R.id.account_type)
        submitButton = findViewById(R.id.submit_account_request)


        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            accountTypeList
        )
        accountType.adapter = arrayAdapter

        dob.isFocusable = false
        dob.isFocusableInTouchMode = false
        dob.setOnClickListener {
            showDatePickerDialog()
        }

        submitButton.setOnClickListener {
            if (validateFields()) {
                insertData()
            }
        }
    }

    private fun populateAccountTypeList() {
        accountTypeList.add("Saving Account")
        accountTypeList.add("Current Account")
    }

    private fun insertData(){

        val selectedId = radioGender.checkedRadioButtonId
        val selectedRadioButton: RadioButton = findViewById(selectedId)

        val enteredName = name.text.toString()
        val randomAccountNumber = generateRandom12DigitNumber()
        val selectedGender = selectedRadioButton.text.toString()
        val enteredMobileNumber = mobileNo.text.toString()
        val enteredEmail = email.text.toString()
        val enteredDOB = dob.text.toString()
        val enteredPan = panCard.text.toString()
        val enteredAddress = address.text.toString()
        val enteredState = state.text.toString()
        val enteredCity = city.text.toString()
        val enteredPin = pinCode.text.toString()
        val ifsc = enteredCity + "123"
        val enteredNomineeAccountType = accountType.selectedItem.toString()

        val dbHelper = OpenAccountTableHelper(this)

        val account = Account(
            randomAccountNumber,
            userId,
            enteredName,
            selectedGender,
            enteredMobileNumber,
            enteredEmail,
            enteredDOB,
            enteredPan,
            enteredAddress,
            enteredState,
            enteredCity,
            enteredPin,
            ifsc,
            enteredCity,
            enteredNomineeAccountType,
            "",
            "pending"
        )

        val success = dbHelper.openAccounnt(account)

        if(success != -1L){
            AlertDialog.Builder(this).create().apply {
                setTitle("Account Open Sucessfully")
                setMessage("Your Account is opened sucessfully wait for confirmation of active status")
                setIcon(R.drawable.sucess)
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _ , _ ->
                    val intent = Intent(this@OpenAccountActivity,HomeActivity::class.java)
                    startActivity(intent)
                }
                show()
            }
        }

        else{
            AlertDialog.Builder(this).create().apply {
                setTitle("Error occured")
                setIcon(R.drawable.error)
                setMessage("Please try again!")
                setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }

    }

    private fun validateFields():Boolean{
        val enteredName = name.text.toString()
        val enteredMobileNumber = mobileNo.text.toString()
        val enteredEmail = email.text.toString()
        val enteredDOB = dob.text.toString()
        val enteredPan = panCard.text.toString()
        val enteredAddress = address.text.toString()
        val enteredState = state.text.toString()
        val enteredCity = city.text.toString()
        val enteredPin = pinCode.text.toString()

        fun isEmailValid(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
            return email.matches(emailRegex.toRegex())
        }

        fun isMobileNumberValid(mobileNumber: String): Boolean {
            val mobileRegex = "^[6-9]\\d{9}\$"
            return mobileNumber.matches(mobileRegex.toRegex())
        }

        fun isPanCardValid(panCard: String): Boolean {
            val panRegex = "[A-Z]{5}[0-9]{4}[A-Z]{1}"
            return panCard.matches(panRegex.toRegex())
        }

        if (enteredName.isEmpty())
        {
            name.error = "Name is required."
            return false
        }

        if(enteredMobileNumber.isEmpty()){
            mobileNo.error = "Mobile Number is required."
            return false
        }
        else if(!isMobileNumberValid(enteredMobileNumber)){
            mobileNo.error = "Enter a valid mobile number."
            return false
        }

        if(enteredEmail.isEmpty()){
            email.error = "Email is required."
            return false
        } else if(!isEmailValid(enteredEmail)){
            email.error = "Enter a valid email address."
            return false
        }

        if(enteredDOB.isEmpty()){
            dob.error = "DOB is required."
            return false
        }

        if (enteredPan.isEmpty()){
            panCard.error = "PanCard No. is required."
            return false
        }
        else if (!isPanCardValid(enteredPan)){
            panCard.error = "Enter a valid PanCard number."
            return false
        }

        if (enteredAddress.isEmpty()){
            address.error = "Address is required."
            return false
        }

        if (enteredState.isEmpty()){
            address.error = "State is required."
            return false
        }

        if (enteredCity.isEmpty()){
            city.error = "City is required."
            return false
        }

        if (enteredPin.isEmpty()){
            pinCode.error = "PinCode is required."
            return false
        }

        return true
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

    private fun generateRandom12DigitNumber(): Long {
        val secureRandom = SecureRandom()
        val lowerBound = 100_000_000_000L
        val upperBound = 999_999_999_999L
        return abs(lowerBound + secureRandom.nextLong() % (upperBound - lowerBound + 1))
    }
}
