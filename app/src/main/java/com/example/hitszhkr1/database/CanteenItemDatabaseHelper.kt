package com.example.hitszhkr1.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class CanteenItemDatabaseHelper(val context: Context, name: String, version: Int) : SQLiteOpenHelper(context,name,null,version) {

    private val create="create table Canteen ("+
            "facilityItemId integer primary key,"+
            "name text,"+
            "avePrice real,"+
            "aveMark real)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Toast.makeText(context, "trying update $create", Toast.LENGTH_SHORT).show()
    }

}