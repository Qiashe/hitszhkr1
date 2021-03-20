package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hitszhkr1.drawerActivities.*
import com.google.android.material.internal.NavigationMenuItemView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mainNavigation.setCheckedItem(R.id.ic_settings)
        button_draw_out.setBackgroundColor(Color.TRANSPARENT)
        nightmode_button.setBackgroundColor(Color.TRANSPARENT)
        scan_button.setBackgroundColor(Color.TRANSPARENT)
        button_draw_out.setOnClickListener {
            mainDrawerLayout.openDrawer(GravityCompat.START)
        }

        bottom_1.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.canteen -> {
                    val intent=Intent(this,CanteenActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }

        //侧滑栏的点击事件处理
        mainNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_settings -> {
                    val intent=Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_help -> {
                    val intent=Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_contact -> {
                    val intent=Intent(this,ContactActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.ic_about -> {
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

    override fun onStart() {
        bottom_1.selectedItemId=R.id.homepage
        super.onStart()
    }

}
