package com.example.sneakerapp

import ApiHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.constants.constants.Companion.BASE_URL


import org.json.JSONArray
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var buttonDatePicker: MaterialButton
    private lateinit var editTextDate: EditText
    private lateinit var spinner: Spinner
    private lateinit var selectedLocation: TextView
    private lateinit var locations: List<CallLog.Locations>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val lintologin = findViewById<MaterialTextView>(R.id.linktologin)
        lintologin.setOnClickListener {
            val intent = Intent(applicationContext, SignInActivity::class.java)
            startActivity(intent)
        }

        val btncreate = findViewById<Button>(R.id.create)
        btncreate.setOnClickListener {
            val surname = findViewById<EditText>(R.id.surname)
            val others = findViewById<EditText>(R.id.others)
            val email = findViewById<EditText>(R.id.email)
            val phone = findViewById<EditText>(R.id.phone)
            val password = findViewById<EditText>(R.id.password)
            val confirm = findViewById<EditText>(R.id.confirm)
//            val loacation = findViewById<>()


            //            regex to check upper case
            val uppercase = Regex(".*[A-Z].*")
            //to check special character
            val specialcharacter = Regex("[^A-Za_z0-9]")

//            regex to check digit
            val digits = Regex(".*\\d.*")
//            regex to check lower case
            val lowercase = Regex("[a-z]")
            //check if password match
            if (password.text.toString() != confirm.text.toString()) {
                //password don't match
                Toast.makeText(applicationContext, "Password don't Match", Toast.LENGTH_SHORT)
                    .show()

            } else if (password.length() < 8) {
                Toast.makeText(
                    applicationContext,
                    "Password must be at least 8 characters long",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (!uppercase.containsMatchIn(password.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    "Password Must Contain Uppercase",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (!specialcharacter.containsMatchIn(password.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    "Password Must Contain Special Character",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (!digits.containsMatchIn(password.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    "Password Must Contain at least a digit",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!lowercase.containsMatchIn(password.text.toString())) {
                Toast.makeText(
                    applicationContext,
                    "Password Must Contain Lowercase",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                //now you can signup
                val helper = ApiHelper(applicationContext)
                val api = BASE_URL + "/usersignup"
                //create a json object
                val body = JSONObject()
                body.put("surname", surname.text.toString())
                body.put("others", others.text.toString())
                body.put("email", email.text.toString())
                body.put("phone", phone.text.toString())
                body.put("password", password.text.toString())
                body.put("location_id", "1")
                helper.post(api, body, object : ApiHelper.CallBack {
                    override fun onSuccess(result: JSONArray?) {
                    }

                    override fun onSuccess(result: JSONObject?) {
//                        posted successful
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFailure(result: String?) {
                        //faild to post
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT)
                            .show()

                    }

                })

            }
        }
    }
}