package com.example.hitszhkr1.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hitszhkr1.objects.FacilityItem
import com.example.hitszhkr1.activity.FacilityItemActivity
import com.example.hitszhkr1.R

class FacilityItemAdapter(private val context: Context, private val item_list : List<FacilityItem>) : RecyclerView.Adapter<FacilityItemAdapter.ViewHolder>() {

    inner class  ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleFacility : TextView = view.findViewById(R.id.title)
        val timeFacility : TextView = view.findViewById(R.id.openTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.facility_item,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val item = item_list[position]
            val intent = Intent(context, FacilityItemActivity::class.java).putExtra("facilityItemId",item.id)
            context.startActivity(intent)
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facilityItem = item_list[position]
        holder.titleFacility.text = facilityItem.title
        holder.timeFacility.text = "营业时间:${facilityItem.time}"
    }

    override fun getItemCount()=item_list.size


}