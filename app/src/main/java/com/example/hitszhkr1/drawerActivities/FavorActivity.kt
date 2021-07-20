package com.example.hitszhkr1.drawerActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.objects.CanteenItem
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.database.FavorDatabaseHelper
import kotlinx.android.synthetic.main.activity_favor.*

class FavorActivity : AppCompatActivity() {

    private var itemList = ArrayList<CanteenItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favor)
        toolbar_favor.title = "我的收藏"
        setSupportActionBar(toolbar_favor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*
        initList()
        val layoutManager = LinearLayoutManager(context)
        fRecycler.layoutManager = layoutManager
        val adapter = context?.let { CanteenItemAdapter(it,itemList,activity = MainActivity()) }
        fRecycler.adapter = adapter
        */
        initList()
        if (itemList.isNotEmpty()){
            Log.d("database","adding")
            val layoutManager = LinearLayoutManager(this)
            favorRecycler.layoutManager = layoutManager
            val adapter = CanteenItemAdapter(this,itemList,FavorActivity())
            favorRecycler.adapter = adapter
        }else{
            Log.d("database","itemList is null")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initList(){
        val dbHelper = FavorDatabaseHelper(this,"Favor.db",1)
        val db = dbHelper.readableDatabase
        if (db != null){
            val cursor = db.query("Favor",null,null,null,null,null,null)

            if (cursor.moveToFirst()){
                do {
                    val canteenItemId = cursor.getInt(cursor.getColumnIndex("canteenItemId"))
                    Log.d("database","read database canteenItemId = $canteenItemId")
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val facilityItemId = cursor.getInt(cursor.getColumnIndex("facilityItemId"))
                    val info = cursor.getString(cursor.getColumnIndex("info"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    val spicyRate = cursor.getDouble(cursor.getColumnIndex("spicyRate"))
                    val sweetRate = cursor.getDouble(cursor.getColumnIndex("sweetRate"))
                    val mark = cursor.getDouble(cursor.getColumnIndex("mark"))
                    val item = CanteenItem(canteenItemId,name,facilityItemId,info,price,spicyRate,sweetRate,mark)
                    itemList.add(item)
                }while (cursor.moveToNext())
            }
        }
    }
}