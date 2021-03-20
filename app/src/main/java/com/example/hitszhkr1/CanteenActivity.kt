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
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                val item = CanteenItem(name,id,id)
                itemList.add(item)
            }while (cursor.moveToNext())
        }
        cursor.close()
//        val i=1
//        val item = CanteenItem("小饼干！",R.drawable.test1,1)
//        repeat(50){
//            itemList.add(item)
//        }
    }

    private fun createCanteenBook(){ //数据库创建
        Toast.makeText(this, "trying", Toast.LENGTH_SHORT).show()
            dbHelper.writableDatabase
    }

    private fun addToCanteenBook(){ //填充数据库中的数据（临时）
        val db=dbHelper.writableDatabase
        val value1 = ContentValues().apply {
            put("about","TEST1,TEST1,TEST1")
            put("canteenNum",1)
            put("location","梨园")
            put("mark",0)
            put("name","阿巴阿巴")
            put("spice",0)
        }
        db.insert("Canteen",null,value1)
        val value2 = ContentValues().apply {
            put("about","TEST2,TEST2,TEST2")
            put("canteenNum",1)
            put("location","梨园")
            put("mark",0)
            put("name","114514!!!")
            put("spice",1)
        }
        db.insert("Canteen",null,value2)
        val value3 = ContentValues().apply {
            put("about","TEST1,TEST1,TEST1")
            put("canteenNum",1)
            put("location","和园")
            put("mark",0)
            put("name","哼哼，啊啊啊啊啊啊啊！")
            put("spice",1)
        }
        db.insert("Canteen",null,value3)
        val value4 = ContentValues().apply {
            put("about","TEST1,TEST1,TEST1")
            put("canteenNum",4)
            put("location","梨园")
            put("mark",5)
            put("name","甜甜花酿鸡")
            put("spice",0)
        }
        db.insert("Canteen",null,value4)
    }

}