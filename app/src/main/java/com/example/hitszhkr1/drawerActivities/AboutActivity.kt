package com.example.hitszhkr1.drawerActivities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar_about.title="关于"
        setSupportActionBar(toolbar_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        text_about.text= "大一立项产品\n" +
                "致力于消除信息不对等，方便日常生活。\n" +
                "项目开源于Gitee、Github\n"+
                "联系我们：aucki6144@gmail.com"
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