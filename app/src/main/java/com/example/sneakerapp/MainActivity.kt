package com.example.sneakerapp

import ApiHelper
import com.example.sneakerapp.adapters.ShoeAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.example.sneakerapp.constants.constants
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.helpers.prefsHelper
import com.example.sneakerapp.models.Shoe
import com.google.android.material.button.MaterialButton
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //Global Declaration - they can be accessed all over this class
    lateinit var itemList: List<Shoe>
    lateinit var labAdapter: ShoeAdapter
//    lateinit var recyclerView: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var swiperefresh: SwipeRefreshLayout

    fun update(){
        //Find Views By ID
        val user = findViewById<MaterialTextView>(R.id.user)
        val signin = findViewById<MaterialButton>(R.id.signin)
        val signout = findViewById<MaterialButton>(R.id.signout)
        val profile = findViewById<MaterialButton>(R.id.profile)

        //Set below 3 Views to GONE/Disappear
        signin.visibility = View.GONE
        signout.visibility = View.GONE
        profile.visibility = View.GONE

        //Access user access token from Prefs
        val token = prefsHelper.getPrefs(applicationContext, "access_token")
        if (token.isEmpty()){
            //If user Token does  not exist, Update user TextView with Not Logged In
            user.text = "Not Logged In"
            //Make sign in button visible
            signin.visibility = View.VISIBLE
            signin.setOnClickListener {
                //Link to Sign in Activity
                startActivity(Intent(applicationContext, SignInActivity::class.java))
            }
        }
        else{
            //If user Token  exist,
            //Make Profile Button visible
            profile.visibility = View.VISIBLE
            profile.setOnClickListener {
                //Link to Member Profile TODO Later
                startActivity(Intent(applicationContext, SignUpActivity::class.java))
            }//end

            //Access username from Prefs
            val surname = prefsHelper.getPrefs(applicationContext, "surname")
            //Update user textView with Logged in User
            user.text = "Welcome $surname"
            //Make signout button visble
            signout.visibility = View.VISIBLE
            //Link to PrefHelper and Clear Prefs
            signout.setOnClickListener{
                prefsHelper.clearPrefs(applicationContext)
                startActivity(intent)
                finishAffinity()
            }
        }
    }//end

    fun requestLocation(){
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                123)
        }//end if
        else {

        }
    }//end function


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestLocation()
        update()

        progress = findViewById(R.id.progressbar)
//        recyclerView = findViewById(R.id.recycler)
        labAdapter = ShoeAdapter(applicationContext)
//        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.setHasFixedSize(true)
        //Call the function
        fetchData()
        swiperefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swiperefresh.setOnRefreshListener {
            fetchData()// fetch data again
        }//end refresh

        //Filter labs
        val etsearch = findViewById<EditText>(R.id.search)
        etsearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(texttyped: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }//end Oncreate

    fun fetchData(){
        //Go to the PAi get the dataapplicationContextthis
        val api = constants.BASE_URL+"/laboratories"
        val helper = ApiHelper(this@MainActivity)
        helper.get(api, object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                //Take above result to adapter
                //Convert Above result from JSON array to LIST<Lab>
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(),
                    Array<Shoe>::class.java).toList()
                //Finally, our adapter has the data
                labAdapter.setListItems(itemList)
                //For the sake of recycling/Looping items, add the adapter to recycler
//                recyclerView.adapter = labAdapter
                progress.visibility = View.GONE
                swiperefresh.isRefreshing = false

            }//end

            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "Error:"+result.toString(),
                    Toast.LENGTH_SHORT).show()
                Log.d("failureerrors", result.toString())
                progress.visibility = View.GONE
            }

        })
    }//end fetch data

    //Filter function
    //justpaste.it/9j21s
    //Filter
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Shoe> = ArrayList()

        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            labAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            labAdapter.filterList(filteredlist)
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.mycart){
//            startActivity(Intent(applicationContext, MyCart::class.java))
//        }
//        return super.onOptionsItemSelected(item)
//    }


    //Start
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) // Access main.xml
        val item: MenuItem = menu!!.findItem(R.id.mycart) //find my cart item
        item.setActionView(R.layout.design) //load the design
        val actionView: View? = item.actionView
        //Access the views in design XML
        val number = actionView?.findViewById<TextView>(R.id.badge)
        val image = actionView?.findViewById<ImageView>(R.id.image)
        //If image is clicked, Link to MyCart
        image?.setOnClickListener {
            startActivity(Intent(applicationContext, MyCart::class.java))
        }
        //load the number of items in Cart icon
        val helper = SQLiteCartHelper(applicationContext)
        number?.text = ""+helper.getNumberOfItems()
        return super.onCreateOptionsMenu(menu) //show options menu
    }
    //End
}
