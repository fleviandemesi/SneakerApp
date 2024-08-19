package com.example.sneakerapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakerapp.R
import com.example.sneakerapp.SingleShoeActivity

import com.example.sneakerapp.models.Shoe

class  ShoeAdapter (var context: Context):
    RecyclerView.Adapter<ShoeAdapter.ViewHolder>(){
    // name of our class is LabAdapter
    //Recycler view.adapter provided by android to work with recyclerview
    // Lab adapter.viewHolder:: it means adapter will work with view holder class named view holder
    // create a list and connect it with our model
    var itemList: List<Shoe> = listOf()// its empty
    //var item lists means the  value can be changed
    // list of means there are no labs in the list initially
    //create a class which will hold our views in single_lab.xml
    //
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    //access single_lab.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //access/inflate the single_item.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singleshoe ,parent, false)
        //pass the single lab to view holder
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // bind the data to viewss from the single_lab.xml
        //fid your three textviews in single_item
        val name = holder.itemView.findViewById<TextView>(R.id.name)
        val brand = holder.itemView.findViewById<TextView>(R.id.brand)
        val price =holder.itemView.findViewById<TextView>(R.id.price)

        // assume one lab, and bind data, it will loop all other labs
        val shoe = itemList[position]
        name.text = shoe.name
        price.text = "KES " +  shoe.price
        brand.text =shoe.brand


        //when one lab is clicked move to lab test activity

        holder.itemView.setOnClickListener{
            // to navigate to each lab test on each shoe click
            // we carry lab_id clicked with bundles(putextra)
            //pass this id along with intent
            val intent = Intent(context, SingleShoeActivity::class.java)
            // we use key 1 to save it
            intent.putExtra("shoe_id",shoe.shoe_id)
//            intent.putExtra("category_id",shoe.category_id)
            intent.putExtra("name",shoe.name)
            intent.putExtra("price",shoe.price)
            intent.putExtra("brand",shoe.brand)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)


        }


    }
    //count the number of items
    override fun getItemCount(): Int {
        return itemList.size//count how many items in each list
    }
    //function filter data
    fun filterList(filterlist:List<Shoe>){
        itemList = filterlist
        notifyDataSetChanged()
    }

    //    Earlier we mention item list is empty
//we will get data from our api, the bring it to the below function
// the data you bring must follow the lab model
    fun setListItems(data: List<Shoe>){
        itemList = data // link our data with the item list
        notifyDataSetChanged()
        // tell us the adapter class that our item list is loaded with data
    }
}