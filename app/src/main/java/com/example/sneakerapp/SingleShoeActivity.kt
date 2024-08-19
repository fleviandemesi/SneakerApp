package com.example.sneakerapp

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.helpers.NetworkHelper
import com.example.sneakerapp.helpers.SQLiteCartHelper

class SingleShoeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_shoe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (NetworkHelper.checkFoeInternet(applicationContext)) {

            val shoename = findViewById<MaterialTextView>(R.id.name)
            val shoebrand = findViewById<MaterialTextView>(R.id.brand)
            val shoeprice = findViewById<MaterialTextView>(R.id.price)

            val cartbtn = findViewById<MaterialButton>(R.id.addcart)

            // Retrieve data from intent extras
            val shoe_id = intent.extras?.getString("shoe_id")
//            val category_id = intent.extras?.getString("category_id")
            val name = intent.extras?.getString("name")
            val brand = intent.extras?.getString("brand")
            val price = intent.extras?.getString("price")


            shoename.text = name
//            category_id.text = category_id
            shoebrand.text = brand
            shoeprice.text = "$price  KES"


            cartbtn.setOnClickListener {
                //call our class called SQLCart helper
                val helper = SQLiteCartHelper(applicationContext)
                try {
                    helper.insertData(
                        shoe_id!!,
                        name!!,
                        brand!!,
                        price!!

                    )
//
                }   // end of try
                catch (e: Exception) {
                    Toast.makeText(applicationContext, "An error Occurred", Toast.LENGTH_SHORT)
                        .show()

                }   //end of catch
            }//end of on click listener
            // test our item count inside our cart
            val helper = SQLiteCartHelper(applicationContext)
            val count = helper.getNumberOfItems()
            Toast.makeText(applicationContext, "Item count is $count", Toast.LENGTH_SHORT).show()


//get all the items
            val items = helper.getAllItems()
            for (item in items) {
                Toast.makeText(applicationContext, "${item.shoe_id}", Toast.LENGTH_SHORT).show()
//
//        }//end of for loop
//        helper.clearCartById("2")
//                helper.totalCoast()


                //e are forced


            }
        }

    }
}