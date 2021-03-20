package com.example.hitszhkr1.drawerActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapse_setting.title="设置"
        
    }
}