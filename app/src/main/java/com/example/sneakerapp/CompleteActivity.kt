package com.example.sneakerapp

import ApiHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sneakerapp.constants.constants
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.helpers.prefsHelper
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class CompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        //Access SQLIte Helper
        val sqllitehelper = SQLiteCartHelper(applicationContext)
        val items = sqllitehelper.getAllItems() //Get All items
        //Generate Invoice No. Check this Function at the bottom of this File
        val invoice_no = generateInvoiceNumber()

        //Save Invoice No  to Prefs
        prefsHelper.savePrefs(applicationContext, "invoice_no", invoice_no)
        val numItems = items.size // Get all items size,
        //Loop each item
        items.forEachIndexed { index, Shoe ->
            //How many Items do you have?
            // Capture test ID/Lab ID at a given Loop
            //Each item has a test_id and lab_id
            val shoe_id = Shoe.category_id


            //Can Toast at this Point
            //Toast.makeText(applicationContext, "Test ID ${test_id} and Lab ID ${lab_id}", Toast.LENGTH_SHORT).show()

            //Capture other details from Preferences, These were saved in different part in this projects
            //NB: You might need to confirm that below were saved in preferences.
            //Retrieve all details from prefs
            val member_id = prefsHelper.getPrefs(this, "user_id")
            val date = prefsHelper.getPrefs(this, "date")
            val time = prefsHelper.getPrefs(this, "time")

            val latitude = prefsHelper.getPrefs(this, "latitude")
            val longitude = prefsHelper.getPrefs(this, "longitude")


            //Access API helper and Post your variables to API
            val helper = ApiHelper(this)
            val api = constants.BASE_URL + "/makeorder"
            val body = JSONObject()
            body.put("user_id", member_id)
            body.put("date", date)
            body.put("time", time)
            body.put("latitude", latitude)
            body.put("longitude", longitude)
            body.put("invoice_no", invoice_no)

            helper.post(api, body, object : ApiHelper.CallBack {
                override fun onSuccess(result: JSONObject?) {
                    //Success Posted
                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()

                }

                override fun onFailure(result: String?) {
                    //Failed to POST
                    Toast.makeText(applicationContext,
                        result.toString(), Toast.LENGTH_SHORT).show()
                    //                    val json = JSONObject(result.toString())
                    //                    val msg = json.opt("msg")
                    //                    //TODO
                    //                    if (msg == "Token has Expired"){
                    //                        PrefsHelper.clearPrefs(applicationContext)
                    //                        startActivity(Intent(applicationContext, SignInActivity::class.java))
                    //                        finishAffinity()
                    //                    }
                }

                override fun onSuccess(result: JSONArray?) {

                }
            })//end post
            //Index counts from zero, Means all lab tests have been posted to API we can now Proceed to Payment
            if (index == (numItems-1)){
                Toast.makeText(applicationContext, "Processing Done", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Payment::class.java))
                finish()
            }//end


        }//end for each
    }//end oncreate

    //Generate Invoice Number Function
    fun generateInvoiceNumber(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val currentTime = Date()
        val timestamp = dateFormat.format(currentTime)

        // You can use a random number or a sequential number to add uniqueness to the invoice number
        // For example, using a random number:
        val random = Random()
        val randomNumber = random.nextInt(1000) // Change the upper bound as needed

        // Combine the timestamp and random number to create the invoice number
        return "INV-$timestamp-$randomNumber" //Unique
    }
    //github.com/modcomlearning/MediLabApp
}//end class