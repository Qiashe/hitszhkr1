package com.example.hitszhkr1.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hitszhkr1.R
import com.example.hitszhkr1.interfaces.AsyncImage
import com.example.hitszhkr1.webfuns.AsyncGetAllImage

class ImageListAdapter(private val imageIdList: List<Int>, val context: Context) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private lateinit var getBitmap : Bitmap

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image : ImageView = view.findViewById(R.id.image_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageID = imageIdList[position]
        Log.d("test","imageId is $imageID")
        val async=AsyncGetAllImage()
        async.execute(imageID)
        async.setOnAsyncResponse(object : AsyncImage{
            override fun onDataReceivedSuccess(data: Bitmap) {
                getBitmap = data
                Glide.with(context).load(getBitmap).into(holder.image)
                Log.d("test","get Bitmap")
            }
            override fun onDataReceivedFailed() {
                Log.d("test","Data received failed")
            }
        })
    }

    override fun getItemCount() = imageIdList.size

}