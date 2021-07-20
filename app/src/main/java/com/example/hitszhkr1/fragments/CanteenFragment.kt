package com.example.hitszhkr1.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.objects.CanteenItem
import com.example.hitszhkr1.activity.MainActivity
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.database.FacilityDatabaseHelper
import com.example.hitszhkr1.popup.SortPopupWindow
import kotlinx.android.synthetic.main.fragment_canteen.*
import java.util.ArrayList

class CanteenFragment : Fragment() {

    private val itemList = ArrayList<CanteenItem>()//itemList为列表

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canteen,container,false)
    }

    @SuppressLint("InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //初始化卡片列表
        initList()
        val layoutManager = LinearLayoutManager(context)
        fRecycler.layoutManager = layoutManager
        val adapter = context?.let { CanteenItemAdapter(it,itemList,activity = MainActivity()) }
        fRecycler.adapter = adapter

    }

    @SuppressLint("Recycle")
    private fun initList(){
        val dbHelper = context?.let { FacilityDatabaseHelper(it,"Canteen.db",1) }
        val database = dbHelper?.writableDatabase
        val cursor = database?.query("Canteen",null,null,null,null,null,null)
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    val canteenItemId = cursor.getInt(cursor.getColumnIndex("canteenItemId"))
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val facilityItemId = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                    val info = cursor.getString(cursor.getColumnIndex("info"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    val spicyRate = cursor.getDouble(cursor.getColumnIndex("spicyRate"))
                    val sweetRate = cursor.getDouble(cursor.getColumnIndex("sweetRate"))
                    val mark = cursor.getDouble(cursor.getColumnIndex("mark"))
                    val item = CanteenItem(canteenItemId,name,facilityItemId,info,price,spicyRate,sweetRate,mark)
                    itemList.add(item)
                }while (cursor.moveToNext())
            }
        }
    }
}