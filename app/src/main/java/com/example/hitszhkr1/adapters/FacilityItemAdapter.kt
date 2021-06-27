package com.example.hitszhkr1.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hitszhkr1.FacilityItem
import com.example.hitszhkr1.FacilityItemActivity
import com.example.hitszhkr1.R

class FacilityItemAdapter(private val context: Context, private val item_list : List<FacilityItem>) : RecyclerView.Adapter<FacilityItemAdapter.ViewHolder>() {

    inner class  ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleFacility : TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.facility_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val item = item_list[position]
            val intent = Intent(context,FacilityItemActivity::class.java).putExtra("ID",item.id)
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facilityItem = item_list[position]
        holder.titleFacility.text = facilityItem.name
    }

    override fun getItemCount()=item_list.size


}