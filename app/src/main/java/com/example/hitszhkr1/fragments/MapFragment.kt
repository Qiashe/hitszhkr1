package com.example.hitszhkr1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.example.hitszhkr1.FacilityItem
import com.example.hitszhkr1.FacilityItemActivity
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //初始化地图
        val mapView = fMap
        mapView.onCreate(savedInstanceState)
        val aMap = mapView.map

        //初始化定位蓝点
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.showMyLocation(true)
        aMap.isMyLocationEnabled = true
        aMap.myLocationStyle = myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18F))

        //绘制Marker
        val facilityList = ArrayList<FacilityItem>()
        val item1 = FacilityItem(LatLng(22.587535,113.967211),"荔园体育场",1)
        val item2 = FacilityItem(LatLng(22.586703,113.968088),"荔园二食堂",2)
        val item3 = FacilityItem(LatLng(22.5865,113.968855),"荔园一食堂",3)
        facilityList.add(item1)
        facilityList.add(item2)
        facilityList.add(item3)

        val markerList = ArrayList<MarkerOptions>()
        for (i in facilityList.indices){
            val item = MarkerOptions().position(facilityList[i].position).title(facilityList[i].name)
            markerList.add(item)
        }
        aMap.addMarkers(markerList,true)

        aMap.setOnInfoWindowClickListener {
            val targetItem = it.title
            for (item in facilityList){
                if (targetItem == item.name){
                    val intent = Intent(context,FacilityItemActivity::class.java).putExtra("ID",item.id)
                    startActivity(intent)
                    break
                }
            }
        }
    }
}