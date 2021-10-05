package com.example.hitszhkr1.drawerActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.activity_night_mode.*
import kotlinx.android.synthetic.main.activity_setting.*

class NightModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night_mode)
        toolbar_switch.title="深色模式"
        setSupportActionBar(toolbar_switch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}