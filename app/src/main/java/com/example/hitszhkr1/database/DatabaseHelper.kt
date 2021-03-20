package com.example.hitszhkr1.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class DatabaseHelper(val context: Context, name: String, createIndex: String, version: Int) : SQLiteOpenHelper(context,name,null,version) {

    private val create=createIndex

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(create)
        Toast.makeText(context, "CREATE$create", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }


}