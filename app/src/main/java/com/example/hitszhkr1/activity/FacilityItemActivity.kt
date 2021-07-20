package com.example.hitszhkr1.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.autonavi.amap.mapcore.interfaces.IUiSettings
import com.bumptech.glide.Glide
import com.example.hitszhkr1.R
import com.example.hitszhkr1.database.CanteenItemDatabaseHelper
import com.example.hitszhkr1.datafuns.Facility
import kotlinx.android.synthetic.main.activity_facility_item.*

class FacilityItemActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_item)

        val facilityItemId = intent.getIntExtra("facilityItemId",-1)
        val facilityItem = Facility(this).getFacilityItem(facilityItemId)
        val mapView =findViewById<MapView>(R.id.itemMap)
        mapView.onCreate(savedInstanceState)
        val aMap=mapView.map
        aMap.isTouchPoiEnable = false

        if (facilityItem != null) {
            toolbar_facility.title = facilityItem.title
            aMap.addMarker(MarkerOptions().position(facilityItem.position))
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(facilityItem.position,17F,0F,0F)))
            facilityTime.text = facilityItem.time
            facilityPrice.text = facilityItem.price.toString()
            val type = Facility(this).getFacilityType(facilityItemId)
            var info = facilityItem.info
            facilityType.text = type
            if (type == "食堂"){
                var aveMark = 0.00
                var avePrice = 0.00
                val dbHelper = CanteenItemDatabaseHelper(this,"CanteenItem.db",Integer.parseInt(getString(R.string.database_canteenItem_version)))
                val database = dbHelper.readableDatabase
                val cursor = database.query("Canteen",null,null,null,null,null,null)
                if (cursor.moveToFirst()){
                    do {
                        val target = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                        if (target == facilityItemId){
                            avePrice = cursor.getDouble(cursor.getColumnIndex("avePrice"))
                            aveMark = cursor.getDouble(cursor.getColumnIndex("aveMark"))
                            break
                        }
                    }while (cursor.moveToNext())
                }
                info = "食堂平均价格：$avePrice\n食堂平均评分：$aveMark\n$info"
            }
            facilityInfo.text = info
        }

        setSupportActionBar(toolbar_facility)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}