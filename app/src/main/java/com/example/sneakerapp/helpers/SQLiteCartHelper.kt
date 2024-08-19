package com.example.sneakerapp.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.sneakerapp.MyCart

import com.example.sneakerapp.models.Shoe

//our class is called sqlitecarthelper and its going to accept 1 parameter called context
class SQLiteCartHelper(context:Context) : SQLiteOpenHelper(context,"cart.db",null,1) {
    //    make context visible to other functons
    val context = context
    override fun onCreate(db: SQLiteDatabase?) {
        // create table if it does not exist
        val createtable = ("CREATE TABLE IF NOT EXISTS cart(shoe_id Integer PRIMARY KEY AUTOINCREMENT,name VARCHAR,price Integer, brand TEXT )")

        db?.execSQL(createtable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Add the price column if it doesn't exist
        db?.execSQL("ALTER TABLE cart ADD COLUMN price INTEGER DEFAULT 0")
    }

    //    insert into cart table
    fun insertData(shoe_id:String,name:String,price:String,brand:String){
        //ask permission to write our db
        val db = this.writableDatabase
        //select before insert to see if Id exists
        val values = ContentValues()
        values.put("shoe_id",shoe_id)
        values.put("name",name)
        values.put("price",price)
        values.put("brand",brand)



        //save/ insert to cart table
        val result: Long = db.insert("cart",null,values)
        if (result < 1){
            println("Failed to Add")
            Toast.makeText(context, "Item Already in Cart", Toast.LENGTH_SHORT).show()
        }else{
            println("Item Added to Cart")
            Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show()
        }// ed of else statement

    }//end of insert data
    //check the no of items saved in our table
    fun getNumberOfItems():Int{
        //get permission to read data base
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from cart",null)
        return result.count
    }//end of getNumberof items
    //    function to clear cart
    fun clearCart(){
//        get permission to write the database
        val db = this.writableDatabase
        db.delete("cart",null, null)
        println("Cart Cleared")
        Toast.makeText(context, "Cart Cleared", Toast.LENGTH_SHORT).show()

        //reload the cart activity
        val intent = Intent(context, MyCart::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }//end of clear cart

    //    fun to retrieve all records
    //we loop in an arraylist
    fun getAllItems(): ArrayList<Shoe>{
//        get permission to write data base
        val db = this.writableDatabase
        val items = ArrayList<Shoe>()
//            create a result
        val result: Cursor = db.rawQuery("select*from cart",null)
//    lets add all rows into the item list
        while(result.moveToNext()){
//
            val model = Shoe()
            model.shoe_id = result.getString(0)//assume one row ,test_id
            model.name = result.getString(1)
            model.price = result.getString(2)
            model.brand = result.getString(3)



//            add model to arraylist
            items.add(model)



        }//end of while loop
        return items

    }//end of get all items

//    delete record by id. deletes one record at a time

    //    fun to remove item
    fun clearCartById(shoe_id: String){
//        get write permission to db
        val db = this.writableDatabase

//    provide the testing id when deleting
        db.delete("cart","shoe_id = ?", arrayOf(shoe_id))
        println("Item Id $shoe_id Removed")
//    Toast.makeText(context, "Item Id $test_id Removed", Toast.LENGTH_SHORT).show()
        //    reload my art after removing an item
//    val intent = Intent(context,MyCart::class.java)
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    context.startActivity(intent)
    }//end of clear cart by idelect price from cart",null)
//    creat a variable called total and a
//
//    //    fun to get the total cost of cart items
//Find Total Cost
fun totalCost(): Double {
    val db = this.readableDatabase
    val result: Cursor = db.rawQuery("select test_cost from cart",
        null)
    //Set total to 0.0
    var total: Double = 0.0
    while (result.moveToNext()){
        //the cursor result returns a Lists of test_cost.
        //Loop through as you add them to total
        total += result.getDouble(0)
    }//end while
    //Return the updated total
    return total
}//End


    }//end function



