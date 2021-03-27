package com.example.hitszhkr1.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hitszhkr1.CanteenActivity
import com.example.hitszhkr1.R
import com.example.hitszhkr1.CanteenItem
import com.example.hitszhkr1.CanteenItemActivity

class canteenItemAdapter(private val context: Context, private val item_list : List<CanteenItem>) : RecyclerView.Adapter<canteenItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageCanteen : ImageView = view.findViewById(R.id.image_canteen)
        val nameCanteen : TextView = view.findViewById(R.id.name_canteen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.canteen_item,parent,false)
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position=holder.adapterPosition
            val item=item_list[position]
            val intent=Intent(context,CanteenItemActivity::class.java).apply {
                putExtra("ID",item.ID)
                putExtra("ImageId",item.imageID)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val canteenItem = item_list[position]
        holder.nameCanteen.text = canteenItem.name
//        holder.imageCanteen.setImageResource(canteenItem.ID)
        Glide.with(context).load(canteenItem.imageID).into(holder.imageCanteen)
    }

    override fun getItemCount()=item_list.size

}