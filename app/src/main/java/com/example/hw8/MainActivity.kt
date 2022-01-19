package com.example.hw8

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group

class MainActivity : AppCompatActivity() {

    private lateinit var fullnameEt: EditText
    lateinit var usernameEt: EditText
    lateinit var emailEt: EditText
    lateinit var passwordEt: EditText
    lateinit var rePasswordEt: EditText

    lateinit var femaleRadioBtn: RadioButton
    lateinit var maleRadioBtn: RadioButton

    lateinit var registerBtn: Button
    lateinit var showInfoBtn: Button
    lateinit var hideInfoBtn: Button
    lateinit var infoGroup: Group

    lateinit var fullnameTv: TextView
    lateinit var usernameTv: TextView
    private lateinit var emailTv: TextView
    lateinit var passwordTv: TextView
    lateinit var genderTv: TextView

    var isOnshow: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        registerBtn.setOnClickListener {
            if (validateInputs()) {
                if (storeData()) notify("Register is down ! ")
                isOnshow = false
            }
        }
        showInfoBtn.setOnClickListener {
            if (!isOnshow) {
                infoGroup.visibility = VISIBLE
                setInfoValue()
                isOnshow = true
            } else {
                isOnshow = false
                notify("Info is already showing")
            }
        }
        hideInfoBtn.setOnClickListener {
            infoGroup.visibility = GONE
            isOnshow = false
        }

    }

    private fun initViews() {

        // init Edittext
        fullnameEt = findViewById(R.id.editTextPersonFullName)
        usernameEt = findViewById(R.id.editTextUsername)
        emailEt = findViewById(R.id.editTextEmail)
        passwordEt = findViewById(R.id.editTextPassword)
        rePasswordEt = findViewById(R.id.editTextRetypePassword)

        // init radios
        femaleRadioBtn = findViewById(R.id.radioBtnFemale)
        maleRadioBtn = findViewById(R.id.radioBtnMale)

        //init Buttons
        registerBtn = findViewById(R.id.btnRegister)
        showInfoBtn = findViewById(R.id.btnShowinfo)
        hideInfoBtn = findViewById(R.id.btnHideinfo)

        //init Info Group
        infoGroup = findViewById(R.id.infoGroup)
        infoGroup.visibility = GONE

        fullnameTv = findViewById(R.id.tvFullnameResult)
        usernameTv = findViewById(R.id.tvUsernameResult)
        passwordTv = findViewById(R.id.tvPasswordResult)
        emailTv = findViewById(R.id.tvUEmailResult)
        genderTv = findViewById(R.id.tvGenderResult)


    }

    //store data if success return true
    private fun storeData(): Boolean {
        val storeDataPreferences = getSharedPreferences("User", MODE_PRIVATE)
        val editor = storeDataPreferences.edit().apply {
            putString("fullname", fullnameEt.text.toString())
            putString("username", usernameEt.text.toString())
            putString("email", emailEt.text.toString())
            putString("password", passwordEt.text.toString())
            putString("gender", getChecked())
        }

        return editor.commit()
    }

    // set info from SharedPreferences
    private fun setInfoValue() {
        getSharedPreferences("User", MODE_PRIVATE).apply {
            fullnameTv.text = getString("fullname", "-")
            usernameTv.text = getString("username", "-")
            emailTv.text = getString("email", "-")
            passwordTv.text = getString("password", "-")
            genderTv.text = getString("gender", "-")
        }

    }

    //  find radio checked
    private fun getChecked(): String {
        if (maleRadioBtn.isChecked) return maleRadioBtn.text.toString()
        return femaleRadioBtn.text.toString()

    }

    //check EditText is blank or not
    private fun EditText.validEditText(): Boolean {
        if (this.text.isBlank()) {
            this.error = "Empty"
            println("${this.text} = true ")
            return false
        }
        return true
    }

    //validate one by one editTexts
    private fun validateInputs(): Boolean {
        var r: Boolean = true

        r = fullnameEt.validEditText() &&
                usernameEt.validEditText() &&
                emailEt.validEditText() &&
                passwordEt.validEditText() &&
                rePasswordEt.validEditText()

        if (rePasswordEt.text.toString() != passwordEt.text.toString()) {
            notify("Password and Re-typed Password not same")
            r = false
        }
        return r

    }

    private fun notify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_tic_tac_toe -> {
                val nextActivity = Intent(this, TicTacToe::class.java)
                startActivity(nextActivity)
                return true
            }
        }
        return false
    }

}