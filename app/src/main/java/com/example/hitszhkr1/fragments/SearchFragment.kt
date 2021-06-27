package com.example.hitszhkr1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.model.LatLng
import com.example.hitszhkr1.CanteenItem
import com.example.hitszhkr1.FacilityItem
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.adapters.FacilityItemAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val canteenList = ArrayList<CanteenItem>()
    private val facilityList = ArrayList<FacilityItem>()
    private var getText = "text"
    private var listIsFacility = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getText = arguments?.getString("text",null).toString()
        listIsFacility = arguments?.getBoolean("listIsFacility",true) == true

        //向服务器请求两个list
        canteenList.add(CanteenItem("野菇鸡肉串",R.drawable.pic_temp_1,1,"荔园",1,0,1))
        canteenList.add(CanteenItem("甜甜花酿鸡",R.drawable.pic_temp_2,2,"荷园",2,0,1))
        canteenList.add(CanteenItem("蒙德土豆饼",R.drawable.pic_temp_3,3,"荷园",3,1,1))
        canteenList.add(CanteenItem("提瓦特煎蛋",R.drawable.pic_temp_4,4,"燕园",1,1,0))
        canteenList.add(CanteenItem("北地烟熏鸡",R.drawable.pic_temp_5,5,"荔园",2,1,0))

        facilityList.add(FacilityItem(LatLng(22.587535,113.967211),"荔园体育场",1))
        facilityList.add(FacilityItem(LatLng(22.586703,113.968088),"荔园二食堂",2))
        facilityList.add(FacilityItem(LatLng(22.5865,113.968855),"荔园一食堂",3))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        sRecycler.layoutManager = layoutManager

        val regex = Regex(getText)

        if (listIsFacility){
            val adapter1 = context?.let { context -> FacilityItemAdapter(context, facilityList.filter { regex.containsMatchIn(it.name) }) }
            sRecycler.adapter = adapter1
        }else{
            val adapter2 = context?.let { context -> CanteenItemAdapter(context, canteenList.filter { regex.containsMatchIn(it.name) }) }
            sRecycler.adapter = adapter2
        }

    }

}