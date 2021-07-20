package com.example.hitszhkr1.datafuns

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.json.JSONArray

class FacilityIdToTitle(context: Context) {
    private val editor: SharedPreferences = context.getSharedPreferences("appData",Context.MODE_PRIVATE)
    private val jsonData = editor.getString("facilityList",null)
    public fun directTranslate(facilityId: Int) : String? {
        var title : String? = null
        val jsonArray = JSONArray(jsonData)
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val facilityItemId = jsonObject.getInt("facilityItemId")
            if (facilityItemId == facilityId){
                title = jsonObject.getString("title")
                break
            }
            Log.d("test","$facilityItemId,$facilityId")
        }
        return title
    }
}