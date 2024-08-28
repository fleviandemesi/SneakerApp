package com.example.sneakerapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.helpers.NetworkHelper
import com.example.sneakerapp.helpers.SQLiteCartHelper

class SingleShoeActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteCartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_shoe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        if (NetworkHelper.checkFoeInternet(applicationContext)) {
//            // Initialize SQLiteCartHelper
//            helper = SQLiteCartHelper(applicationContext)
//
//            val shoename = findViewById<MaterialTextView>(R.id.name)
//            val shoebrand = findViewById<MaterialTextView>(R.id.brand)
//            val shoeprice = findViewById<MaterialTextView>(R.id.price)
//            val shoephoto = findViewById<ImageView>(R.id.shoeimage)
//            val cartbtn = findViewById<MaterialButton>(R.id.addcart)
//
//            // Retrieve data from intent extras
//            val shoe_id = intent.extras?.getString("shoe_id")?.toIntOrNull() ?: return
//            val category_id = intent.extras?.getString("category_id")?.toIntOrNull() ?: return
//            val name = intent.extras?.getString("name") ?: return
//            val price = intent.extras?.getString("price")?.toIntOrNull() ?: return
//            val description = intent.extras?.getString("description") ?: return
//            val brand = intent.extras?.getString("brand") ?: return
//            val photo = intent.extras?.getString("photo") ?: return
//            val quantity = intent.extras?.getString("quantity")?.toIntOrNull() ?: return
//
//            shoename.text = name
//            shoebrand.text = brand
//            shoeprice.text = "$price KES"
//
//            Glide.with(this)
//                .load(photo)
//                .apply(
//                    RequestOptions()
//                        .placeholder(R.drawable.error_image) // Placeholder image while loading
//                        .error(R.drawable.error_image) // Error image if loading fails
//                )
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                        Log.e("SingleShoeActivity", "Error loading image", e)
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                        return false
//                    }
//                })
//                .into(shoephoto)
//
//            cartbtn.setOnClickListener {
//                try {
//                    helper.insertData(
//                        shoe_id,
//                        category_id,
//                        name,
//                        price,
//                        description,
//                        brand,
//                        photo,
//                        quantity
//                    )
//                    Toast.makeText(applicationContext, "Item Added to Cart", Toast.LENGTH_SHORT).show()
//                } catch (e: Exception) {
//                    Log.e("SingleShoeActivity", "Error inserting data", e)
//                    Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            // Test item count inside the cart
//            val count = helper.getNumberOfItems()
//            Toast.makeText(applicationContext, "Item count is $count", Toast.LENGTH_SHORT).show()
//
//            // Get all items and display their IDs
//            val items = helper.getAllItems()
//            for (item in items) {
//                Toast.makeText(applicationContext, "Shoe ID: ${item.shoe_id}", Toast.LENGTH_SHORT).show()
//            }
//
//            // Get total cost of items
//            val totalCost = helper.totalCost()
//            Toast.makeText(applicationContext, "Total cost is $totalCost", Toast.LENGTH_SHORT).show()
//        }
    }
}
