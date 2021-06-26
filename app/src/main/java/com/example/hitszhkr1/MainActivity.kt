package com.example.hitszhkr1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapFragment
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.example.hitszhkr1.drawerActivities.AboutActivity
import com.example.hitszhkr1.drawerActivities.ContactActivity
import com.example.hitszhkr1.drawerActivities.HelpActivity
import com.example.hitszhkr1.drawerActivities.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_draw_out.setBackgroundColor(Color.TRANSPARENT)
        nightmode_button.setBackgroundColor(Color.TRANSPARENT)
        scan_button.setBackgroundColor(Color.TRANSPARENT)

        //默认加载设施界面
        val fragment = com.example.hitszhkr1.fragments.MapFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainer,fragment)
        transaction.commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        button_draw_out.setOnClickListener {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        }

        //底栏点击事件监听
        bottom_1.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homepage -> {
                    if (bottom_1.selectedItemId != R.id.homepage){
                        val fragment1 = com.example.hitszhkr1.fragments.MapFragment()
                        val fragmentManager1 = supportFragmentManager
                        val transaction1 = fragmentManager1.beginTransaction()
                        transaction1.replace(R.id.mainFragmentContainer,fragment1)
                        transaction1.commit()
                    }
                    true
                }
                R.id.canteen -> {
                    if(bottom_1.selectedItemId != R.id.canteen){
                        val fragment2 = com.example.hitszhkr1.fragments.CanteenFragment()
                        val fragmentManager2 = supportFragmentManager
                        val transaction2 = fragmentManager2.beginTransaction()
                        transaction2.replace(R.id.mainFragmentContainer,fragment2)
                        transaction2.commit()
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
                    val intent=Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_help -> {
                    //帮助界面
                    val intent=Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_contact -> {
                    //联系界面
                    val intent=Intent(this,ContactActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_about -> {
                    //关于界面
                    val intent=Intent(this,AboutActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
//
//    override fun onStart() {
//        //设置默认选择homePage
//        bottom_1.selectedItemId=R.id.homepage
//        super.onStart()
//    }

}