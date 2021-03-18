package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hitszhkr1.drawerActivities.AboutActivity
import com.example.hitszhkr1.drawerActivities.ContactActivity
import com.example.hitszhkr1.drawerActivities.HelpActivity
import com.example.hitszhkr1.drawerActivities.SettingsActivity
import com.google.android.material.internal.NavigationMenuItemView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mainNavigation.setCheckedItem(R.id.ic_settings)
        mainNavigation.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_settings -> {
                    val intent=Intent(this, SettingsActivity::class.java)
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
}
