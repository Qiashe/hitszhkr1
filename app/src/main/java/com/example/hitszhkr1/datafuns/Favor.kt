package com.example.hitszhkr1.datafuns

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.hitszhkr1.database.FavorDatabaseHelper

class Favor(context: Context) {

    private val dbHelper = FavorDatabaseHelper(context,"Favor.db",1)
    private val db = dbHelper.writableDatabase

    /**
     * 通过canteenItemId在Favor数据库中检查该项目是否被收藏
     * 是将返回1，否则将返回0
     */
    fun ifLiked(canteenItemId : Int) : Int{
        val cursor = db.query("Favor", null,null,null,null,null,null)
        if (cursor.moveToFirst()){
            do {
                val targetId = cursor.getInt(cursor.getColumnIndex("canteenItemId"))
                Log.d("database","targetId is $targetId")
                if (targetId == canteenItemId)
                    return 1
            }while (cursor.moveToNext())
        }
        cursor.close()
        return 0
    }

    fun likeCancel(canteenItemId : Int) : Int{
        return try {
            db.delete("Favor", "canteenItemId == ?", arrayOf("$canteenItemId"))
            0
        }catch (e:Exception){
            e.printStackTrace()
            -1
        }
    }

    fun likeInsert(value : ContentValues) : Int {
        return try {
            db.insert("Favor", null, value)
            1
        }catch (e:Exception){
            e.printStackTrace()
            -1
        }
    }
}