package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_facility_item.*

class FacilityItemActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_item)
        //获取
        val id = intent.getIntExtra("ID",-1)

        //temp
        val position = LatLng(22.587535,113.967211)

        setSupportActionBar(fToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this).load(R.drawable.abstract_blue_02).into(fImage)
        fCollapse.title = "名称+$id"

        val mapView =findViewById<MapView>(R.id.itemMap)
        mapView.onCreate(savedInstanceState)
        val aMap=mapView.map
        aMap.addMarker(MarkerOptions().position(position))
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(position,17F,0F,0F)))
        aMap.isTouchPoiEnable = false

        facilityTime.text = "8:00~24:00"
        facilityPrice.text = "0"
        facilityInfo.text = "把生命的突泉捧在我手里,\n"+
                "我只觉得它来得新鲜,\n"+
                "我只觉得它来得新鲜,\n"+
                "是浓烈的酒，清新的泡沫,\n"+
                "注入我的奔波、劳作、冒险,\n"+
                "仿佛前人从未经临的园地,\n"+
                "就要展现在我的面前,\n"+
                "但如今，突然面对着坟墓,\n"+
                "我冷眼向过去稍稍回顾,\n"+
                "只见它曲折灌溉的悲喜,\n"+
                "都消失在一片亘古的荒漠,\n"+
                "这才知道我的全部努力,\n"+
                "不过完成了普通的生活."

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