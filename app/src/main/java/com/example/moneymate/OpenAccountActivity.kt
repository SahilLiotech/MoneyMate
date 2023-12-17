package com.example.moneymate

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.moneymate.data.OpenAccountTableHelper
import com.example.moneymate.model.Account
import java.security.SecureRandom


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
    private lateinit var nomineeName: EditText
    private lateinit var nomineeAccountNo: EditText
    private lateinit var accountType: Spinner
    private lateinit var submitButton: Button
    private lateinit var accountTypeList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_account)
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
        nomineeName = findViewById(R.id.nominee_name)
        nomineeAccountNo = findViewById(R.id.nominee_account_no)
        accountType = findViewById(R.id.account_type)
        submitButton = findViewById(R.id.submit_account_request)


        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            accountTypeList
        )
        accountType.adapter = arrayAdapter

        submitButton.setOnClickListener {
            insertData()
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
        val branch = enteredCity
        val enteredNomineeName = nomineeName.text.toString()
        val enteredNomineeAccountNo = nomineeAccountNo.text.toString()
        val enteredNomineeAccountType = accountType.selectedItem.toString()

        val dbHelper = OpenAccountTableHelper(this)

        val account = Account(
            randomAccountNumber,
            0,
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
            branch,
            enteredNomineeName,
            enteredNomineeAccountNo,
            enteredNomineeAccountType,
            System.currentTimeMillis().toString(),
            "Pending"
        )

        val success = dbHelper.openAccounnt(account)

        if(success){
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

    private fun generateRandom12DigitNumber(): Long {
        val secureRandom = SecureRandom()
        val lowerBound = 100_000_000_000L
        val upperBound = 999_999_999_999L
        return Math.abs(lowerBound + secureRandom.nextLong() % (upperBound - lowerBound + 1))
    }
}
