package com.example.sneakerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.models.Shoe

class SingleShoeCartAdapter (var context: Context):
    RecyclerView.Adapter<SingleShoeCartAdapter.ViewHolder>() {

    //Create a List and connect it with our model
    var itemList : List<Shoe> = listOf() //Its empty
    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_cart_item,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Find your 3 text views
        val name = holder.itemView.findViewById<MaterialTextView>(R.id.name)
        val brandname = holder.itemView.findViewById<MaterialTextView>(R.id.brand)
        val shoeprice = holder.itemView.findViewById<MaterialTextView>(R.id.price)
        //Assume one Lab
        val item = itemList[position]
        name.text = item.name
        shoeprice.text = item.price+" KES"
        brandname.text = item.brand

        //Find remove button and set Listener
        val remove = holder.itemView.findViewById<MaterialButton>(R.id.remove)
        remove.setOnClickListener {
            val shoe_id = item.shoe_id
            val helper = SQLiteCartHelper(context)
            helper.clearCartById(shoe_id)
            Toast.makeText(context, "remove", Toast.LENGTH_SHORT).show()
            //The Item is Removed.
            //Go to Helper and reload the MyCart Activity in clearCartById fun
        }


    }

    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }

    //No need to filter the data, Its Just a shopping Cart

    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Shoe>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}