package com.example.sneakerapp.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.sneakerapp.models.Shoe

class SQLiteCartHelper(context: Context) : SQLiteOpenHelper(context, "cart3.db", null, 1) {

    private val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE IF NOT EXISTS cart (
                shoe_id INTEGER PRIMARY KEY AUTOINCREMENT,
                category_id INTEGER,
                name TEXT,
                price INTEGER,
                description TEXT,
                brand TEXT,
                photo TEXT,
                quantity INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)
    }

    fun getNumberOfItems(): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT COUNT(*) FROM cart", null)
        cursor.use {
            it.moveToFirst()
            return it.getInt(0)
        }
    }

    fun clearCart() {
        val db = this.writableDatabase
        db.delete("cart", null, null)
        Toast.makeText(context, "Cart cleared", Toast.LENGTH_SHORT).show()
    }

    fun getAllItems(): ArrayList<Shoe> {
        val db = this.readableDatabase
        val items = ArrayList<Shoe>()

        // Use try-with-resources to ensure cursor is closed properly
        val cursor: Cursor = db.rawQuery("SELECT * FROM cart", null)
        cursor.use {
            val columnNames = it.columnNames
            Log.d("SQLiteCartHelper", "Column names: ${columnNames.joinToString()}")

            val shoeIdIndex = it.getColumnIndex("shoe_id")
            val categoryIdIndex = it.getColumnIndex("category_id")
            val nameIndex = it.getColumnIndex("name")
            val priceIndex = it.getColumnIndex("price")
            val descriptionIndex = it.getColumnIndex("description")
            val brandIndex = it.getColumnIndex("brand")
            val photoIndex = it.getColumnIndex("photo")
            val quantityIndex = it.getColumnIndex("quantity")

            if (shoeIdIndex == -1 || categoryIdIndex == -1 || nameIndex == -1 ||
                priceIndex == -1 || descriptionIndex == -1 || brandIndex == -1 ||
                photoIndex == -1 || quantityIndex == -1) {
                throw IllegalStateException("One or more columns are missing: shoeIdIndex=$shoeIdIndex, categoryIdIndex=$categoryIdIndex, nameIndex=$nameIndex, priceIndex=$priceIndex, descriptionIndex=$descriptionIndex, brandIndex=$brandIndex, photoIndex=$photoIndex, quantityIndex=$quantityIndex")
            }

            while (it.moveToNext()) {
                val model = Shoe().apply {
                    shoe_id = it.getString(shoeIdIndex)
                    category_id = it.getString(categoryIdIndex)
                    name = it.getString(nameIndex)
                    price = it.getInt(priceIndex).toString()
                    description = it.getString(descriptionIndex)
                    brand = it.getString(brandIndex)
                    photo = it.getString(photoIndex)
                    quantity = it.getInt(quantityIndex).toString()
                }
                items.add(model)
            }
        }
        return items
    }

    fun totalCost(): Double {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT price FROM cart", null)
        cursor.use {
            var total = 0.0
            while (it.moveToNext()) {
                total += it.getInt(0).toDouble() // Assuming price is stored as INTEGER
            }
            return total
        }
    }

    fun clearCartById(shoe_id: String) {
        val db = this.writableDatabase
        db.delete("cart", "shoe_id=?", arrayOf(shoe_id))
        Toast.makeText(context, "Item ID $shoe_id removed", Toast.LENGTH_SHORT).show()
    }

    fun insertData(
        shoe_id: Int,
        category_id: Int,
        name: String,
        price: Int,
        description: String,
        brand: String,
        photo: String,
        quantity: Int
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("shoe_id", shoe_id)
            put("category_id", category_id)
            put("name", name)
            put("price", price)
            put("description", description)
            put("brand", brand)
            put("photo", photo)
            put("quantity", quantity)
        }
        val result = db.insert("cart", null, values)
        if (result == -1L) {
            Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
