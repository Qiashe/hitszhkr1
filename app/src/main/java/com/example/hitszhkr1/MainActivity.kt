package com.example.hitszhkr1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.example.hitszhkr1.drawerActivities.AboutActivity
import com.example.hitszhkr1.drawerActivities.ContactActivity
import com.example.hitszhkr1.drawerActivities.HelpActivity
import com.example.hitszhkr1.drawerActivities.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_draw_out.setBackgroundColor(Color.TRANSPARENT)
        nightmode_button.setBackgroundColor(Color.TRANSPARENT)
        scan_button.setBackgroundColor(Color.TRANSPARENT)

        //初始化Map
        val mapView =findViewById<MapView>(R.id.map)
        mapView.onCreate(savedInstanceState)
        val aMap=mapView.map


        //定位蓝点
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000)
        myLocationStyle.showMyLocation(true)
        aMap.myLocation
        aMap.myLocationStyle = myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        aMap.isMyLocationEnabled = true
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17F))

        //Marker绘制
        val latLng = LatLng(22.587535,113.967211)
        val marker = MarkerOptions().position(latLng).title("体育场").snippet("DefaultMarker").visible(true)
        aMap.addMarker(marker)

        //temp
        val facilityList = ArrayList<FacilityItem>()
        val item1 = FacilityItem(LatLng(22.587535,113.967211),"荔园体育场",1)
        val item2 = FacilityItem(LatLng(22.586703,113.968088),"荔园二食堂",2)
        val item3 = FacilityItem(LatLng(22.5865,113.968855),"荔园一食堂",3)
        facilityList.add(item1)
        facilityList.add(item2)
        facilityList.add(item3)

        val markerList = ArrayList<MarkerOptions>()
        for (i in facilityList.indices){
            val item=MarkerOptions().position(facilityList[i].position).title(facilityList[i].name)
            markerList.add(item)
        }
        aMap.addMarkers(markerList,true)

        //弹出窗口点击监听，跳转具体页面
        aMap.setOnInfoWindowClickListener {
            val targetItem = it.title
            for (i in facilityList){
                if(targetItem == i.name){
                    Toast.makeText(this, "open activity id=${i.id}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,FacilityItemActivity::class.java).putExtra("ID",i.id)
                    startActivity(intent)
                    break
                }
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        button_draw_out.setOnClickListener {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        }

        bottom_1.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.canteen -> {
                    val intent=Intent(this,CanteenActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }

        //侧滑栏的点击事件处理
        mainNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_settings -> {
                    val intent=Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_help -> {
                    val intent=Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_contact -> {
                    val intent=Intent(this,ContactActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_about -> {
                    val intent=Intent(this,AboutActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onStart() {
        bottom_1.selectedItemId=R.id.homepage
        super.onStart()
    }

}