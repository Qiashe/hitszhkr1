package com.example.hitszhkr1.datafuns

import android.content.Context
import com.amap.api.maps.model.LatLng
import com.example.hitszhkr1.R
import com.example.hitszhkr1.database.FacilityDatabaseHelper
import com.example.hitszhkr1.objects.FacilityItem

class Facility (context: Context) {

    private val databaseHelper = FacilityDatabaseHelper(context,"Facility.db",Integer.parseInt(context.getString(
        R.string.database_facility_version)))
    private val database = databaseHelper.readableDatabase

    /**
     * 通过传入一个facilityItemId来获取一个FacilityItem对象
     */
    fun getFacilityItem(facilityItemId : Int) : FacilityItem? {
        val cursor = database.query("Facility",null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val target = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                if (target == facilityItemId) {
                    val title = cursor.getString(cursor.getColumnIndex("title"))
                    val info = cursor.getString(cursor.getColumnIndex("info"))
                    val time = cursor.getString(cursor.getColumnIndex("time"))
                    val positionX = cursor.getDouble(cursor.getColumnIndex("positionX"))
                    val positionY = cursor.getDouble(cursor.getColumnIndex("positionY"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    return FacilityItem(
                        LatLng(positionY, positionX),
                        facilityItemId,
                        title,
                        time,
                        price,
                        info
                    )
                }
            }while (cursor.moveToNext())
        }
        return null
    }

    /**
     * 根据正则表达式关键词匹配，判断设施类型
     */
    fun getFacilityType(facilityItemId: Int) : String {
        val facilityItem = getFacilityItem(facilityItemId)
        val regex1 = Regex("食堂")
        val regex2 = Regex("运动")
        val regex3 = Regex("体育")
        val regex4 = Regex("杠")
        val regex5 = Regex("书")
        if (facilityItem != null) {
            if (regex1.containsMatchIn(facilityItem.title))
                return "食堂"
            if (regex2.containsMatchIn(facilityItem.title) || regex3.containsMatchIn(facilityItem.title) || regex4.containsMatchIn(facilityItem.title))
                return "运动器材"
            if (regex4.containsMatchIn(facilityItem.title))
                return "教学设施"
        }
        return "其他设施"
    }
}