package com.example.hitszhkr1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.adapters.canteenItemAdapter
import kotlinx.android.synthetic.main.activity_canteen.*
import java.util.ArrayList

class CanteenActivity : AppCompatActivity() {

    val itemList = ArrayList<CanteenItem>()

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
        initList()
        val layoutManager=LinearLayoutManager(this)
        recycler_canteen.layoutManager=layoutManager
        val adapter=canteenItemAdapter(this,itemList)
        recycler_canteen.adapter=adapter
    }

    private fun initList(){
        val item = CanteenItem("小饼干！",R.drawable.test1,1)
        repeat(50){
            itemList.add(item)
        }
    }


}