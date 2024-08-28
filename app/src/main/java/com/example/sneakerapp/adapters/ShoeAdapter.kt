package com.example.sneakerapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.sneakerapp.R
import com.example.sneakerapp.SingleShoeActivity
import com.example.sneakerapp.models.Shoe
import com.bumptech.glide.request.target.Target


class ShoeAdapter(private val context: Context) : RecyclerView.Adapter<ShoeAdapter.ViewHolder>() {

    private var itemList: List<Shoe> = listOf()
    private var originalList: List<Shoe> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.shoeimage) // Make sure this ID is correct in your layout
        val name: TextView = itemView.findViewById(R.id.name)
        val brand: TextView = itemView.findViewById(R.id.brand)
        val price: TextView = itemView.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singl, parent, false) // Ensure this XML layout exists
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoe = itemList[position]
        holder.name.text = shoe.name
        holder.brand.text = shoe.brand
        holder.price.text = "KES ${shoe.price}"

        // Load the shoe photo using Glide
        Glide.with(context)
            .load(shoe.photo)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.shoe1) // Placeholder image while loading
                    .error(R.drawable.shoe1) // Error image if loading fails
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    Log.e("ShoeAdapter", "Error loading image", e)
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }
            })
            .into(holder.photo)

        Log.d("ShoeAdapter", "Loading image URL: ${shoe.photo}")

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SingleShoeActivity::class.java).apply {
                putExtra("shoe_id", shoe.shoe_id)
                putExtra("category_id", shoe.category_id)
                putExtra("name", shoe.name)
                putExtra("price", shoe.price)
                putExtra("description", shoe.description)
                putExtra("brand", shoe.brand)
                putExtra("photo",shoe.photo)
                putExtra("quantity", shoe.quantity)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun filterList(filterList: List<Shoe>) {
        itemList = filterList
        notifyDataSetChanged()
    }

    fun filterByCategory(categoryId: Int) {
        itemList = if (categoryId == -1) {
            originalList
        } else {
            originalList.filter { it.category_id.toInt() == categoryId }
        }
        notifyDataSetChanged()
    }

    fun setListItems(data: List<Shoe>) {
        originalList = data
        itemList = data
        notifyDataSetChanged()
    }
}
