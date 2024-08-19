package com.example.sneakerapp

import ApiHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.constants.constants
import com.example.sneakerapp.helpers.prefsHelper
import org.json.JSONArray
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lintoregister = findViewById<MaterialTextView>(R.id.linktoregister)
        lintoregister.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        //fetch to edit text
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val login = findViewById<MaterialButton>(R.id.login)
        login.setOnClickListener{
            //specify your member signin endpoint
            val api = constants.BASE_URL +"/usersigin"
            //specify apihelper object
            val helper = ApiHelper(applicationContext)
            //create a JSON object of email and password
            val body = JSONObject()
            body.put("email",email.text.toString())
            body.put("password",password.text.toString())
            helper.post(api,body,object: ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {

                }

                override fun onSuccess(result: JSONObject?) {
                    //check if access_token exists in response
                    if(result!!.has("access_token")){
                        //access token found,login success
                        //access token and member from the JSON return
                        val access_token = result.getString("access_token")
                        val member = result.getString("member")
                        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                        //save access token to shared prefs
                        prefsHelper.savePrefs(applicationContext,"access_token",access_token)

//                        convert member to an object
                        val memberObject = JSONObject(member)

                        val memberemail = memberObject.getString("email")
                        val surname = memberObject.getString("surname")
                        //save member_id to shared prefs

                        prefsHelper.savePrefs(applicationContext,"email",memberemail)

                        prefsHelper.savePrefs(applicationContext,"surname",surname)

                        //save member with shared prefs
                        prefsHelper.savePrefs(applicationContext,"userObject",member)

                        //Toast.makeText(applicationContext, "$access_token", Toast.LENGTH_SHORT).show()
//                        redirect to main activity upon succesful login
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }else{
//                        No access token found , login failed
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(result: String?) {

                }

            })
        }



    }
}

