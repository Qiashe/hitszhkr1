package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.database.DatabaseHelper
import com.example.hitszhkr1.popup.SortPopupWindow
import kotlinx.android.synthetic.main.activity_canteen.*
import java.util.ArrayList

class CanteenActivity : AppCompatActivity() {

    private val itemList = ArrayList<CanteenItem>()//itemList为列表
    private val createIndex="create table Canteen ("+
            "id integer primary key autoincrement," +
            "name text," +
            "mark integer," +
            "spice integer," +
            "sweet integer," +
            "location text," +
            "canteenNum integer," +
            "reviewNum integer," +
            "imageID integer," +
            "price real," +
            "about text)"
    private val dbVersion=1
    private val dbHelper=DatabaseHelper(this,"canteen.db",createIndex,dbVersion)
    private lateinit var sort_with_location_popup : PopupWindow
    private lateinit var sort_with_taste_popup : PopupWindow
    private lateinit var sort_method_popup : PopupWindow



    @SuppressLint("SetTextI18n")
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

        //ADAPT卡片列表RECYCLERVIEW
        val layoutManager=LinearLayoutManager(this)
        recycler_canteen.layoutManager=layoutManager
        var adapter=CanteenItemAdapter(this,itemList)
        recycler_canteen.adapter=adapter

        var test = "test"
        //搜索功能
        search2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (test != ""){
                    val regex = Regex (test)
                    val tempList= itemList.filter { regex.containsMatchIn(it.name) }
                    val newAdapter=CanteenItemAdapter(this@CanteenActivity,tempList)
                    recycler_canteen.adapter=newAdapter
                    sort_with_location_popup.dismiss()
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //通过首字符筛选内容
                if (query != null) {
                    test = query
                }
                return false
            }
        })


        //弹出窗口初始化
        val popViewParent = LayoutInflater.from(this).inflate(R.layout.activity_canteen,null)
        val locationSortPopView = LayoutInflater.from(this).inflate(R.layout.popup_sort_location,null)
        val tasteSortPopView = LayoutInflater.from(this).inflate(R.layout.popup_sort_taste,null)
        val methodSortPopView = LayoutInflater.from(this).inflate(R.layout.popup_sort_method,null)
        sort_with_location_popup = SortPopupWindow(this,locationSortPopView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        sort_with_taste_popup = SortPopupWindow(this,tasteSortPopView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        sort_method_popup = SortPopupWindow(this,methodSortPopView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        sort_with_location_popup.isFocusable = true
        sort_with_location_popup.isTouchable = true
        sort_with_taste_popup.isFocusable = true
        sort_with_taste_popup.isTouchable = true
        sort_method_popup.isFocusable = true
        sort_method_popup.isTouchable = true

        val locationSortContentView = sort_with_location_popup.contentView
        val locationSortButton0=locationSortContentView.findViewById<Button>(R.id.sl0)
        val locationSortButton1=locationSortContentView.findViewById<Button>(R.id.sl1)
        val locationSortButton2=locationSortContentView.findViewById<Button>(R.id.sl2)
        val locationSortButton3=locationSortContentView.findViewById<Button>(R.id.sl3)
        sort_with_location.setOnClickListener {
            sort_with_location_popup.showAsDropDown(sort_with_location)
        }

        locationSortButton0.setBackgroundColor(Color.TRANSPARENT)
        locationSortButton1.setBackgroundColor(Color.TRANSPARENT)
        locationSortButton2.setBackgroundColor(Color.TRANSPARENT)
        locationSortButton3.setBackgroundColor(Color.TRANSPARENT)

        locationSortButton0.setOnClickListener {
            //全部地点
            sort_with_location.text="全部地点"
            recycler_canteen.adapter=adapter
            sort_with_location_popup.dismiss()
        }
        locationSortButton1.setOnClickListener {
            //仅看荔园
            sort_with_location.text="仅看荔园"
            val tempListLS1= itemList.filter { it.location == "荔园" }
            val newAdapter5=CanteenItemAdapter(this,tempListLS1)
            recycler_canteen.adapter=newAdapter5
            sort_with_location_popup.dismiss()
        }
        locationSortButton2.setOnClickListener {
            //仅看荷园
            val tempListLS2= itemList.filter { it.location == "荷园" }
            val newAdapter6=CanteenItemAdapter(this,tempListLS2)
            recycler_canteen.adapter=newAdapter6
            sort_with_location_popup.dismiss()
        }
        locationSortButton3.setOnClickListener {
            //仅看燕园
            val tempListLS3= itemList.filter { it.location == "燕园" }
            val newAdapter7=CanteenItemAdapter(this,tempListLS3)
            recycler_canteen.adapter=newAdapter7
            sort_with_location_popup.dismiss()
        }

        val tasteSortContentView = sort_with_taste_popup.contentView
        val tasteSortButton0=tasteSortContentView.findViewById<Button>(R.id.st0)
        val tasteSortButton1=tasteSortContentView.findViewById<Button>(R.id.st1)
        val tasteSortButton2=tasteSortContentView.findViewById<Button>(R.id.st2)
        sort_with_taste.setOnClickListener {
            sort_with_taste_popup.showAsDropDown(sort_with_taste)
        }
        tasteSortButton0.setBackgroundColor(Color.TRANSPARENT)
        tasteSortButton1.setBackgroundColor(Color.TRANSPARENT)
        tasteSortButton2.setBackgroundColor(Color.TRANSPARENT)
        tasteSortButton0.setOnClickListener {
            //全部口味
            sort_with_taste.text="全部口味"
            recycler_canteen.adapter=adapter
            sort_with_taste_popup.dismiss()
        }
        tasteSortButton1.setOnClickListener {
            //拒绝辛辣
            sort_with_taste.text="拒绝辛辣"
            val tempListLS1= itemList.filter {it.spice == 0}
            val newAdapter0=CanteenItemAdapter(this,tempListLS1)
            recycler_canteen.adapter=newAdapter0
            sort_with_taste_popup.dismiss()
        }
        tasteSortButton2.setOnClickListener {
            //拒绝甜食
            sort_with_taste.text="拒绝甜食"
            val tempListLS2= itemList.filter { it.sweet == 0 }
            val newAdapter1=CanteenItemAdapter(this,tempListLS2)
            recycler_canteen.adapter=newAdapter1
            sort_with_taste_popup.dismiss()
        }

        val sortMethodView = sort_method_popup.contentView
        val sortMethodButton1=sortMethodView.findViewById<Button>(R.id.sm1)
        val sortMethodButton2=sortMethodView.findViewById<Button>(R.id.sm2)
        val sortMethodButton3=sortMethodView.findViewById<Button>(R.id.sm3)
        sort_method.setOnClickListener {
            sort_method_popup.showAsDropDown(sort_method)
        }
        sortMethodButton1.setBackgroundColor(Color.TRANSPARENT)
        sortMethodButton2.setBackgroundColor(Color.TRANSPARENT)
        sortMethodButton3.setBackgroundColor(Color.TRANSPARENT)
        sortMethodButton1.setOnClickListener {
            //推荐排序
            sort_method.text="推荐排序"
            recycler_canteen.adapter=adapter
            sort_method_popup.dismiss()
        }
        sortMethodButton2.setOnClickListener {
            //按人气排序，待实现
            sort_method.text="人气优先"
            val newAdapter3=CanteenItemAdapter(this,itemList)
            recycler_canteen.adapter=newAdapter3
            sort_method_popup.dismiss()
        }
        sortMethodButton3.setOnClickListener {
            //按评分排序
            sort_method.text="评分优先"
            val newListSM3 = itemList.sortedByDescending { canteenItem -> canteenItem.mark }
            val newAdapter4=CanteenItemAdapter(this,newListSM3)
            recycler_canteen.adapter=newAdapter4
            sort_method_popup.dismiss()
        }


        sort_with_location.text="全部地点"
        sort_with_taste.text="全部口味"
        sort_method.text="推荐排序"

        //筛选排序功能

        sort_with_location.setBackgroundColor(Color.TRANSPARENT)
        sort_with_taste.setBackgroundColor(Color.TRANSPARENT)
        sort_method.setBackgroundColor(Color.TRANSPARENT)




        createCanteenBook() //数据库创建
        //addToCanteenBook() //填充数据库中的数据（临时）
        initList()  //初始化卡片列表


    }

    private fun initList(){ //初始化卡片列表
        val db=dbHelper.writableDatabase
        val cursor = db.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val name=cursor.getString(cursor.getColumnIndex("name"))
                val iid=cursor.getInt(cursor.getColumnIndex("imageID"))
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                val loc=cursor.getString(cursor.getColumnIndex("location"))
                val mark=cursor.getInt(cursor.getColumnIndex("mark"))
                val spice=cursor.getInt(cursor.getColumnIndex("spice"))
                val sweet=cursor.getInt(cursor.getColumnIndex("sweet"))
                val item = CanteenItem(name,iid,id,loc,mark,spice,sweet)
                itemList.add(item)
            }while (cursor.moveToNext())
        }
        cursor.close()
    }

    private fun createCanteenBook(){ //数据库创建
            dbHelper.writableDatabase
    }
}