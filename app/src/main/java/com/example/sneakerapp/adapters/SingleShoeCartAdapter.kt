package com.example.sneakerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.example.sneakerapp.helpers.SQLiteCartHelper
import com.example.sneakerapp.models.Shoe
import com.squareup.picasso.Picasso

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
        val photo = holder.itemView.findViewById<ImageView>(R.id.shoeimage)
        val shoeprice = holder.itemView.findViewById<MaterialTextView>(R.id.price)
        //Assume one Lab
        val shoe = itemList[position]
        name.text = shoe.name
        shoeprice.text = shoe.price+" KES"
        brandname.text = shoe.brand

        // Load the image using Picasso
        val imageUrl = shoe.photo
        if (imageUrl.isNullOrEmpty()) {
            // Handle empty or null image URL
            photo.setImageResource(R.drawable.error_image) // Set a default image
        } else {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.shoe5) // Placeholder image
                .error(R.drawable.error_image) // Error image
                .into(photo)
        }

        //Find remove button and set Listener
        val remove = holder.itemView.findViewById<MaterialButton>(R.id.remove)
        remove.setOnClickListener {
            val shoe_id = shoe.shoe_id
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