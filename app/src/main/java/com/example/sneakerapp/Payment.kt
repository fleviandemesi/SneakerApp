package com.example.sneakerapp

import ApiHelper
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sneakerapp.constants.constants
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.helpers.prefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONArray
import org.json.JSONObject

class Payment :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        //Find TextView with id textpay
        val textpay = findViewById<MaterialTextView>(R.id.textpay)
        //Access helper
        val helper = SQLiteCartHelper(applicationContext)
        textpay.text = "Please Pay KES "+helper.totalCost() //Set Total Cost in TextView

        //Find Button and set Listener
        val btnpay = findViewById<MaterialButton>(R.id.btnpay)
        btnpay.setOnClickListener {
            //Find the phone EditText
            val phone = findViewById<TextInputEditText>(R.id.phone)
            //Access base URL
            val api = constants.BASE_URL+"/payment"
            //Create JSON
            val body = JSONObject()
            //Put variables phone and amount
            body.put("phone", phone.text.toString())//254
//            body.put("amount", helper.totalcost())
            body.put("amount", "1")  //For Testing

            //Get from invoice_no from prefs
            val invoice_no = prefsHelper.getPrefs(this, "invoice_no")
            //put invoice in body
            body.put("invoice_no", invoice_no)// get from prefs

            //Access Helper
            val apihelper = ApiHelper(applicationContext)
            //Post body to API
            apihelper.post2(api, body, object : ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {

                }

                override fun onSuccess(result: JSONObject?) {
                    //SUccess
                    Toast.makeText(applicationContext, result.toString(),
                        Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(result: String?) {
                    //Failure
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
            })
        }//end listener
    }//end oncreate
}//end class