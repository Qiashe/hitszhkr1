package com.example.hitszhkr1.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hitszhkr1.R
import com.example.hitszhkr1.database.CanteenDatabaseHelper
import com.example.hitszhkr1.database.CanteenItemDatabaseHelper
import com.example.hitszhkr1.database.FacilityDatabaseHelper
import com.example.hitszhkr1.database.FavorDatabaseHelper
import com.example.hitszhkr1.drawerActivities.*
import com.example.hitszhkr1.fragments.SearchFragment
import com.example.hitszhkr1.interfaces.AsyncResponse
import com.example.hitszhkr1.webfuns.AsyncGetJson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.net.URL
import java.util.*

/**
 * 程序主Activity
 * 可在其中的mainFragmentContainer中加载Fragment控件
 * 完成所有程序所需数据库的创建
 * =>使用SQLite存储的内容：
 * Favor（用户收藏）
 * Canteen（食堂属性）
 * CanteenItem（食堂列表菜品项）
 * FacilityItem（设施列表项）
 * =>使用SharedPreference存储的内容
 * 数据更新flag（appFlag）
 * json型原始数据（appData）
 */
class MainActivity : AppCompatActivity()  {

    private var nowPage = 1
    private var searchOn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_draw_out.setBackgroundColor(Color.TRANSPARENT)

        checkRefresh()
        createDatabases(this)
        createLocalUserId(this)

        //监听左上角按钮
        button_draw_out.setOnClickListener {
            search.clearFocus()
            if (searchOn == 1){
                searchOn = 0
                search.setQuery("",false)
                button_draw_out.setImageResource(R.drawable.logo)
                if(nowPage == 1){
                    loadFacilityPage()
                }else{
                    loadCanteenPage()
                }
            }else{
                mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        //底栏点击事件监听
        bottom_1.setOnNavigationItemSelectedListener {
            search.clearFocus()
            when(it.itemId) {
                R.id.homepage -> {
                    if (nowPage == 2){
                        searchOn = 0
                        search.setQuery("",false)
                        button_draw_out.setImageResource(R.drawable.logo)
                        nowPage = 1
                        loadFacilityPage()
                    }
                    true
                }
                R.id.canteen -> {
                    if (nowPage == 1){
                        searchOn = 0
                        search.setQuery("",false)
                        button_draw_out.setImageResource(R.drawable.logo)
                        nowPage = 2
                        loadCanteenPage()
                    }
                    true
                }
                else -> {
                    true
                }
            }
        }

        //侧滑栏点击事件监听
        mainNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_settings -> {
                    //设置界面
                    startActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                R.id.ic_about -> {
                    //关于界面
                    startActivity(Intent(this,AboutActivity::class.java))
                    true
                }
                R.id.ic_favor -> {
                    //我的收藏
                    startActivity(Intent(this, FavorActivity::class.java))
                    true
                }
                R.id.ic_night -> {
                    startActivity(Intent(this,NightModeActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }
        }

        //搜索栏监听
        var getText = "text"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("text",getText)
                loadSearchPage(bundle)
                search.clearFocus()
                button_draw_out.setImageResource(R.drawable.ic_back)
                searchOn = 1
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) { getText = newText }
                return false
            }
        })


    }

    /**
     * 顺次检查facilityList、canteenList、canteenData本地数据的更新情况
     * 将请求到的Json数据与本地数据做比对
     */
    private fun checkRefresh(){

        val readEditor = getSharedPreferences("appdata",Context.MODE_PRIVATE)
        val writeEditor = getSharedPreferences("appdata",Context.MODE_PRIVATE).edit()
        val dbHelper1 = FacilityDatabaseHelper(this,"Facility.db",1)
        val dbHelper2 = CanteenDatabaseHelper(this,"Canteen.db",1)
        val dbHelper3 = CanteenItemDatabaseHelper(this,"CanteenItem.db",1)
        val database1 = dbHelper1.writableDatabase
        val database2 = dbHelper2.writableDatabase
        val database3 = dbHelper3.writableDatabase
        val asyncGetJson1 = AsyncGetJson()
        val asyncGetJson2 = AsyncGetJson()
        val asyncGetJson3 = AsyncGetJson()

        //检查FacilityList
        asyncGetJson1.execute(URL(getString(R.string.url_get_facility_list)))
        try {
            asyncGetJson1.setOnAsyncResponse(object : AsyncResponse {
                override fun onDataReceivedSuccess(data: String) {
                    val localData = readEditor.getString("facilityList","NULL")
                    if (localData != data){
                        writeEditor.putString("facilityList",data).apply()
                        //更新Facility数据库
                        database1.delete("Facility",null,null)
                        val jsonArray = JSONArray(data)
                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val value = ContentValues().apply {
                                put("facilityItemId",jsonObject.getInt("facilityItemId"))
                                put("title",jsonObject.getString("title"))
                                put("info",jsonObject.getString("info"))
                                put("positionX",jsonObject.getDouble("positionX"))
                                put("positionY",jsonObject.getDouble("positionY"))
                                put("price",jsonObject.getDouble("price"))
                                put("time",jsonObject.getString("time"))
                            }
                            database1.insert("Facility",null,value)
                        }
                    }
                    runOnUiThread {
                        loadFacilityPage()
                    }
                }
                override fun onDataReceivedFailed() {
                    Log.d("web","Data Receive Fail")
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }

        //检查CanteenList
        asyncGetJson2.execute(URL(getString(R.string.url_get_canteen_list)))
        try {
            asyncGetJson2.setOnAsyncResponse( object : AsyncResponse{
                override fun onDataReceivedSuccess(data: String) {
                    val localData = readEditor.getString("canteenList","NULL")
                    if (localData != data){
                        writeEditor.putString("canteenList",data).apply()
                        //更新Canteen数据库
                        database2.delete("Canteen",null,null)
                        val jsonArray = JSONArray(data)
                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val value = ContentValues().apply {
                                put("canteenItemId",jsonObject.getInt("canteenItemId"))
                                put("name",jsonObject.getString("name"))
                                put("facilityItemId",jsonObject.getInt("facilityItemId"))
                                put("info",jsonObject.getString("info"))
                                put("price",jsonObject.getDouble("price"))
                                put("spicyRate",jsonObject.getDouble("price"))
                                put("sweetRate",jsonObject.getDouble("price"))
                                put("mark",jsonObject.getDouble("price"))
                            }
                            database2.insert("Canteen",null,value)
                        }
                    }
                }
                override fun onDataReceivedFailed() {
                    Log.d("web","Data Receive Fail")
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }

        //检查CanteenData
        asyncGetJson3.execute(URL(getString(R.string.url_get_canteen_data)))
        try {
            asyncGetJson3.setOnAsyncResponse( object : AsyncResponse{
                override fun onDataReceivedSuccess(data: String) {
                    val localData = readEditor.getString("canteenData","NULL")
                    if (localData != data){
                        writeEditor.putString("canteenData",data).apply()
                        //更新CanteenData(CanteenItemDatabase)数据库
                        database3.delete("Canteen",null,null)
                        val jsonArray = JSONArray(data)
                        for (i in 0 until jsonArray.length()){
                            val jsonObject = jsonArray.getJSONObject(i)
                            val value = ContentValues().apply {
                                put("facilityItemId",jsonObject.getInt("facilityItemId"))
                                put("name",jsonObject.getString("title"))
                                put("avePrice",jsonObject.getDouble("price"))
                                put("aveMark",jsonObject.getDouble("mark"))
                            }
                            database3.insert("Canteen",null,value)
                        }
                    }
                }
                override fun onDataReceivedFailed() {
                    Log.d("web","Data Receive Fail")
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
     * 加载MapFragment界面
     * 数据将由MapFragment从数据库读取
     */
    private fun loadFacilityPage(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer,com.example.hitszhkr1.fragments.MapFragment())
        transaction.commit()
    }

    /**
     * 加载CanteenFragment界面
     * 数据将由CanteenFragment从数据库读取
     */
    private fun loadCanteenPage(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer,com.example.hitszhkr1.fragments.CanteenFragment())
        transaction.commit()
    }

    private fun loadSearchPage(bundle: Bundle){
        val fragment = SearchFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer,fragment).commit()
        fragment.arguments = bundle
    }

}

/**
 * 创建本地SQLite数据库
 * 数据库：Favor，存储用户收藏的canteenItem对象，数据原型：CanteenItem
 * 数据库：Canteen，存储各个食堂的信息，数据原型：(facilityItemId : Int , name : String , avePrice : Double , aveMark : Double)
 * 数据库：CanteenItem，存储各个菜品项的信息，数据原型：CanteenItem
 * 数据库：Facility，存储各个设施项的信息，数据原型：(facilityItemId : Int , title : String , info : String , positionX : Double , positionY : Double)
 */
private fun createDatabases(context: Context){

    //创建Favor数据库
    val dbHelper1 = FavorDatabaseHelper(context,"Favor.db",1)
    dbHelper1.writableDatabase

    //创建Canteen数据库
    val dbHelper2 = CanteenDatabaseHelper(context,"Canteen.db",1)
    dbHelper2.writableDatabase

    //创建CanteenItem数据库
    val dbHelper3 = CanteenItemDatabaseHelper(context,"CanteenItem.db",1)
    dbHelper3.writableDatabase

    //创建FacilityItem数据库
    val dbHelper4 = FacilityDatabaseHelper(context,"Facility.db",1)
    dbHelper4.writableDatabase

}

/**
 * 随即生成用户ID
 */
private fun createLocalUserId(context: Context){
    val editor1 = context.getSharedPreferences("appdata",Context.MODE_PRIVATE)
    val userId = editor1.getInt("userId",-1)
    if (userId == -1){
        val editor2 =context.getSharedPreferences("appdata",Context.MODE_PRIVATE).edit()
        val time = Date().time.toString()
        val length = time.length
        val str1 = time.substring(length-8,length-4)
        var str2 = time.substring(length-4,length)
        if (str2.first() == '0'){
            str2 = "1$str2"
        }
        Log.d("test",time)
        Log.d("test",str2+str1)
        editor2.putInt("userId",Integer.parseInt(str2+str1)).apply()
    }

}