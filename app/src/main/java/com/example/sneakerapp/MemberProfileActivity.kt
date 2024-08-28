package com.example.sneakerapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sneakerapp.helpers.prefsHelper

class MemberProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_member_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //fetch member ids
        val profilename = findViewById<TextView>(R.id.profile_name)
        val profileemail = findViewById<TextView>(R.id.profile_email)

        //Access username from Prefs
        val surname = prefsHelper.getPrefs(applicationContext, "surname")
        //Update user textView with Logged in User
        profilename.text = " $surname"

        //Access username from Prefs
        val email = prefsHelper.getPrefs(applicationContext, "email")
        //Update user textView with Logged in User
        profileemail.text = " $email"
    }
    }

