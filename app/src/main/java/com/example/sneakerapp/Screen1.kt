package com.example.sneakerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sneakerapp.helpers.NetworkHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class Screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Check For Internet Connection
        if (!NetworkHelper.checkFoeInternet(applicationContext)) {
            Toast.makeText(applicationContext, "Not Internet", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, Screen1::class.java))
            finish()
        } else {
            //There is Internet Connection
            //Find Views by ID
            val create = findViewById<Button>(R.id.create)
            create.setOnClickListener {
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
            }// End


            val signin = findViewById<Button>(R.id.login)
            signin.setOnClickListener {
                startActivity(Intent(applicationContext, SignInActivity::class.java))
            }// End
        }
    }
}