package com.example.hitszhkr1.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.maps.model.LatLng
import com.example.hitszhkr1.objects.CanteenItem
import com.example.hitszhkr1.objects.FacilityItem
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.adapters.FacilityItemAdapter
import com.example.hitszhkr1.datafuns.Facility
import kotlinx.android.synthetic.main.facility_item.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONArray

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
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val layoutManager1 = LinearLayoutManager(context)
        sRecycler1.layoutManager = layoutManager1
        val adapter1 = context?.let { FacilityItemAdapter(it,facilityList) }
        sRecycler1.adapter = adapter1

        val layoutManager2 = LinearLayoutManager(context)
        sRecycler2.layoutManager = layoutManager2
        val adapter2 = context?.let { activity?.let { it1 ->
            CanteenItemAdapter(it,canteenList,
                it1
            )
        } }
        sRecycler2.adapter = adapter2

        Log.d("test",getText)
        val regex = Regex(getText)
        getFacilityList(regex)
        getCanteenList(regex)
        sText1.text = "设施的搜索结果:${facilityList.size}条"
        sText2.text = "菜品的搜索结果:${canteenList.size}条"
    }

    private fun getFacilityList(regex: Regex){
        val editor = context?.getSharedPreferences("appdata", Context.MODE_PRIVATE)
        val facilityData = editor?.getString("facilityList","NULL")
        if (facilityData != null && facilityData != "NULL"){
            val jsonArray = JSONArray(facilityData)
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val target = jsonObject.getString("title")
                Log.d("test","$getText compare $target")
                if (regex.containsMatchIn(target)){
                    val facilityItemId = jsonObject.getInt("facilityItemId")
                    val title = jsonObject.getString("title")
                    val info = jsonObject.getString("info")
                    val positionX = jsonObject.getDouble("positionX")
                    val positionY = jsonObject.getDouble("positionY")
                    val price = jsonObject.getDouble("price")
                    val time = jsonObject.getString("time")
                    val item = FacilityItem(LatLng(positionY,positionX),facilityItemId,title,time,price,info)
                    facilityList.add(item)
                }
            }
        }
    }

    private fun getCanteenList(regex: Regex){
        val editor = context?.getSharedPreferences("appdata", Context.MODE_PRIVATE)
        val canteenData = editor?.getString("canteenList","NULL")
        if (canteenData != null && canteenData != "NULL"){
            val jsonArray = JSONArray(canteenData)
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val target = jsonObject.getString("name")
                if (regex.containsMatchIn(target)){
                    val canteenItemId = jsonObject.getInt("canteenItemId")
                    val name = jsonObject.getString("name")
                    val facilityItemId = jsonObject.getInt("facilityItemId")
                    val info = jsonObject.getString("info")
                    val price = jsonObject.getDouble("price")
                    val spicyRate = jsonObject.getDouble("spicyRate")
                    val sweetRate = jsonObject.getDouble("sweetRate")
                    val mark = jsonObject.getDouble("mark")
                    val item = CanteenItem(canteenItemId, name, facilityItemId, info, price, spicyRate, sweetRate, mark)
                    canteenList.add(item)
                }
            }
        }
    }

}