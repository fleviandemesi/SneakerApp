package com.example.sneakerapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerapp.adapters.SingleShoeCartAdapter
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.helpers.prefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MyCart : AppCompatActivity() {

    private lateinit var helper: SQLiteCartHelper
    private lateinit var checkoutButton: MaterialButton
    private lateinit var totalTextView: MaterialTextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SingleShoeCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)
        setupWindowInsets()

        helper = SQLiteCartHelper(applicationContext)
        checkoutButton = findViewById(R.id.checkout)
        totalTextView = findViewById(R.id.total)
        recyclerView = findViewById(R.id.recycler)

        setupRecyclerView()
        updateTotalCost()
        setupCheckoutButton()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        adapter = SingleShoeCartAdapter(applicationContext)

        val items = helper.getAllItems()
        if (items.isEmpty()) {
            Toast.makeText(applicationContext, "Your Cart is Empty", Toast.LENGTH_LONG).show()
            checkoutButton.visibility = View.GONE
        } else {
            adapter.setListItems(items)
            recyclerView.adapter = adapter
        }
    }

    private fun updateTotalCost() {
        val totalCost = helper.totalCost()
        totalTextView.text = "Total Cost: $totalCost"
        checkoutButton.visibility = if (totalCost > 0) View.VISIBLE else View.GONE
    }

    private fun setupCheckoutButton() {
        checkoutButton.setOnClickListener {
            val token = prefsHelper.getPrefs(applicationContext, "access_token")
            if (token.isEmpty()) {
                Toast.makeText(applicationContext, "Not Logged In", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, SignInActivity::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, CheckOutActivity::class.java))
                Toast.makeText(applicationContext, "Logged In", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clearcart -> {
                helper.clearCart()
                true
            }
            R.id.backtoLabs -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
