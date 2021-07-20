package com.example.hitszhkr1.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.example.hitszhkr1.activity.FacilityItemActivity
import com.example.hitszhkr1.R
import com.example.hitszhkr1.database.FacilityDatabaseHelper
import com.example.hitszhkr1.datafuns.Facility
import com.example.hitszhkr1.objects.FacilityItem
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    private var facilityList = ArrayList<FacilityItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //初始化地图控件
        val mapView: MapView = fMap
        val aMap = mapView.map
        mapView.onCreate(savedInstanceState)
        aMap.isMyLocationEnabled = true
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18F))
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.showMyLocation(true)
        aMap.myLocationStyle = myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)

        //初始化指针列表
        val markerList = ArrayList<MarkerOptions>()
        for (i in facilityList.indices){
            val item = MarkerOptions().position(facilityList[i].position).title(facilityList[i].title)
            markerList.add(item)
        }
        aMap.addMarkers(markerList,true)

        aMap.setOnInfoWindowClickListener {
            val targetItem = it.title
            for (item in facilityList){
                if (targetItem == item.title){
                    val intent = Intent(context, FacilityItemActivity::class.java).putExtra("facilityItemId",item.id)
                    startActivity(intent)
                    break
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createMarker()
    }

    @SuppressLint("Recycle")
    private fun createMarker(){
        val dbHelper = context?.let { FacilityDatabaseHelper(it,"Facility.db",1) }
        val database = dbHelper?.writableDatabase
        val cursor = database?.query("Facility",null,null,null,null,null,null)
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {
                    val positionX = cursor.getDouble(cursor.getColumnIndex("positionX"))
                    val positionY = cursor.getDouble(cursor.getColumnIndex("positionY"))
                    val position = LatLng(positionY,positionX)
                    val facilityItemId = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val info = cursor.getString(cursor.getColumnIndex("info"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    val time = cursor.getString(cursor.getColumnIndex("time"))
                    val item = FacilityItem(position,facilityItemId,title,time,price,info)
                    facilityList.add(item)
                }while (cursor.moveToNext())
            }
        }
    }
}