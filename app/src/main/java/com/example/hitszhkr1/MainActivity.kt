package com.example.hitszhkr1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.hitszhkr1.drawerActivities.AboutActivity
import com.example.hitszhkr1.drawerActivities.ContactActivity
import com.example.hitszhkr1.drawerActivities.HelpActivity
import com.example.hitszhkr1.drawerActivities.SettingActivity
import com.example.hitszhkr1.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var nowPage = 1
    private var searchOn = 0

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
            if (searchOn == 1){
                searchOn = 0
                if(nowPage == 1){
                    val fragment1 = com.example.hitszhkr1.fragments.MapFragment()
                    val fragmentManager1 = supportFragmentManager
                    val transaction1 = fragmentManager1.beginTransaction()
                    transaction1.replace(R.id.mainFragmentContainer,fragment1)
                    transaction1.commit()
                }else{
                    val fragment2 = com.example.hitszhkr1.fragments.CanteenFragment()
                    val fragmentManager2 = supportFragmentManager
                    val transaction2 = fragmentManager2.beginTransaction()
                    transaction2.replace(R.id.mainFragmentContainer,fragment2)
                    transaction2.commit()
                }
                button_draw_out.setImageResource(R.drawable.ic_index)
            }else{
                mainDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

        //底栏点击事件监听
        bottom_1.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homepage -> {
                    nowPage = 1
                    val fragment1 = com.example.hitszhkr1.fragments.MapFragment()
                    val fragmentManager1 = supportFragmentManager
                    val transaction1 = fragmentManager1.beginTransaction()
                    transaction1.replace(R.id.mainFragmentContainer,fragment1)
                    transaction1.commit()
                    true
                }
                R.id.canteen -> {
                    nowPage = 2
                    val fragment2 = com.example.hitszhkr1.fragments.CanteenFragment()
                    val fragmentManager2 = supportFragmentManager
                    val transaction2 = fragmentManager2.beginTransaction()
                    transaction2.replace(R.id.mainFragmentContainer,fragment2)
                    transaction2.commit()
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

        //搜索栏监听,搜索设施
        var getText = "text"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val fragment0 = SearchFragment()
                val fragmentManager0 = supportFragmentManager
                val transaction0 = fragmentManager0.beginTransaction()
                transaction0.replace(R.id.mainFragmentContainer, fragment0)
                transaction0.commit()
                val bundle = Bundle()
                bundle.putString("text",getText)
                bundle.putBoolean("listIsFacility",(bottom_1.selectedItemId == R.id.homepage))
                fragment0.arguments = bundle
                searchOn = 1
                button_draw_out.setImageResource(R.drawable.ic_back)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) { getText = newText }
                return false
            }
        })
    }

}

