package com.example.sneakerapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerapp.adapters.ShoeAdapter
import com.example.sneakerapp.adapters.SingleShoeCartAdapter
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.helpers.prefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //put total cost in a textview
        val helper = SQLiteCartHelper(applicationContext)
        val checkout = findViewById<MaterialButton>(R.id.checkout)
        if (helper.totalCost() == 0.0){
            checkout.visibility = View.GONE
        }//end
        checkout.setOnClickListener {
            //Using Prefs check if token exists
            val token = prefsHelper.getPrefs(applicationContext, "access_token")
            if (token.isEmpty()){
                //Token does not exist, meaning Not Logged In
                Toast.makeText(applicationContext, "Not Logged In",
                    Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            }
            else {
                //Token Exists, meaning Logged In we Go to Next step
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, "Logged In", Toast.LENGTH_SHORT).show()
            }
        }//end


        val total = findViewById<MaterialTextView>(R.id.total)

        total.text = "Total: "+helper.totalCost()

        //Find recycler
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.setHasFixedSize(true)
        //Access adapter and provide it with from getAllItems
        if(helper.getNumberOfItems() == 0){
            Toast.makeText(applicationContext, "Your Cart is Empty",
                Toast.LENGTH_LONG).show()
        }
        else {
            val adapter = SingleShoeCartAdapter(applicationContext)
            adapter.setListItems(helper.getAllItems())//pass data
            recycler.adapter = adapter //link adapter to recycler
        }
    }

    //Activate Options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Below code Loads the cart XML
        menuInflater.inflate(R.menu.cart, menu)
        return super.onCreateOptionsMenu(menu) //Creates an Options Menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Below code makes id clear cart clickablea and clears the cart using helper.clearCart()
        if (item.itemId == R.id.clearcart){
            val helper = SQLiteCartHelper(applicationContext)
            helper.clearCart()
        }
        //backtoLabs takes us back to MainActivity
        if (item.itemId == R.id.backtoLabs){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

//    override fun onBackPressed() {
//        val i = Intent(applicationContext, MainActivity::class.java)
//        startActivity(i)
//        finishAffinity()
    }



