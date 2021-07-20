package com.example.hitszhkr1.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hitszhkr1.R
import com.example.hitszhkr1.objects.CanteenItem
import com.example.hitszhkr1.activity.CanteenItemActivity
import com.example.hitszhkr1.database.CanteenItemDatabaseHelper
import com.example.hitszhkr1.interfaces.AsyncImage
import com.example.hitszhkr1.webfuns.AsyncGetSingleImage
import java.lang.Exception
import java.net.URL

class CanteenItemAdapter(private val context: Context, private val item_list : List<CanteenItem>, private val activity: Activity) : RecyclerView.Adapter<CanteenItemAdapter.ViewHolder>() {

    private lateinit var getBitmap : Bitmap
    private lateinit var canteenName : String
    private var avePrice = 0.00
    private var aveMark = 0.00

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageCanteen : ImageView = view.findViewById(R.id.image_canteen)
        val nameCanteen : TextView = view.findViewById(R.id.name_canteen)
        var tagCanteen : TextView = view.findViewById(R.id.tag_canteen)
        val markCanteen : TextView = view.findViewById(R.id.mark_canteen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.canteen_item,parent,false)
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position=holder.adapterPosition
            val item=item_list[position]
            val intent=Intent(context, CanteenItemActivity::class.java).apply {
                putExtra("canteenItemId",item.canteenItemId)
                putExtra("name",item.name)
                putExtra("facilityItemId",item.facilityItemId)
                putExtra("info",item.info)
                putExtra("price",item.price)
                putExtra("spicyRate",item.spicyRate)
                putExtra("sweetRate",item.sweetRate)
                putExtra("mark",item.mark)
                putExtra("canteenName",canteenName)
                putExtra("avePrice",avePrice)
                putExtra("aveMark",aveMark)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val canteenItem = item_list[position]
        val dbHelper = CanteenItemDatabaseHelper(context,"CanteenItem.db",Integer.parseInt(context.getString(R.string.database_canteenItem_version)))
        val database = dbHelper.readableDatabase
        val cursor = database.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val target = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                if (target == canteenItem.facilityItemId){
                    canteenName = cursor.getString(cursor.getColumnIndex("name"))
                    avePrice = cursor.getDouble(cursor.getColumnIndex("avePrice"))
                    aveMark = cursor.getDouble(cursor.getColumnIndex("aveMark"))
                    break
                }
            }while (cursor.moveToNext())
        }
        val title = canteenItem.name
        val mark = canteenItem.mark.toString()
        holder.tagCanteen.text = canteenName
        holder.nameCanteen.text = title
        holder.markCanteen.text = mark

        /*
        网络请求
        通过AsyncGetSingleImage()方法可直接通过name属性获取CanteenItem的单张图片
         */
        val asyncGetSingleImage = AsyncGetSingleImage()
        asyncGetSingleImage.execute(URL(context.getString(R.string.url_get_canteen_image_list)+title))
        try {
            asyncGetSingleImage.setOnAsyncResponse( object : AsyncImage {
                override fun onDataReceivedSuccess(data: Bitmap) {
                    getBitmap = data
                    activity.runOnUiThread{
                        Glide.with(context).load(getBitmap).into(holder.imageCanteen)
                    }
                }

                override fun onDataReceivedFailed() {
                    Log.d("web","Fail when loading image json data")
                }

            })
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("web","ERROR WHEN LOADING IMAGE!")
        }


    }

    override fun getItemCount()=item_list.size

}