package com.example.hitszhkr1

import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.adapters.canteenItemAdapter
import com.example.hitszhkr1.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_canteen.*
import java.util.ArrayList

class CanteenActivity : AppCompatActivity() {

    private val itemList = ArrayList<CanteenItem>()
    private val createIndex="create table Canteen ("+
            "id integer primary key autoincrement," +
            "name text," +
            "mark integer," +
            "spice integer," +
            "location text," +
            "canteenNum integer," +
            "reviewNum integer," +
            "imageID integer," +
            "price real," +
            "about text)"
    private val dbVersion=1
    private val dbHelper=DatabaseHelper(this,"canteen.db",createIndex,dbVersion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canteen)
        button_back.setBackgroundColor(Color.TRANSPARENT)
        bottom_2.selectedItemId=R.id.canteen
        bottom_2.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homepage -> {
                    finish()
                    true
                }
                else -> {
                    false
                }
            }
        } //底部导航栏监听

        button_back.setOnClickListener {
            finish()
        } //顶部导航栏back按钮监听


        createCanteenBook() //数据库创建
        //addToCanteenBook() //填充数据库中的数据（临时）
        initList()  //初始化卡片列表

        //ADAPT卡片列表RECYCLERVIEW
        val layoutManager=LinearLayoutManager(this)
        recycler_canteen.layoutManager=layoutManager
        val adapter=canteenItemAdapter(this,itemList)
        recycler_canteen.adapter=adapter

    }

    private fun initList(){ //初始化卡片列表
        val db=dbHelper.writableDatabase
        val cursor = db.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val name=cursor.getString(cursor.getColumnIndex("name"))
                val iid=cursor.getInt(cursor.getColumnIndex("imageID"))
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                val item = CanteenItem(name,iid,id)
                itemList.add(item)
            }while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun createCanteenBook(){ //数据库创建
        Toast.makeText(this, "trying", Toast.LENGTH_SHORT).show()
            dbHelper.writableDatabase
        Toast.makeText(this, "tried", Toast.LENGTH_SHORT).show()

    }


}