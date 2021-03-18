package com.example.hitszhkr1.drawerActivities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_about.view.*

class AboutActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapse_about.title="关于软件"

        text_about.text= "《沪杭车中》\n" +
                "\n" +
                "作者/徐志摩\n" +
                "\n" +
                "匆匆匆！催催催！\n" +
                "\n" +
                "一卷烟，一片山，\n" +
                "\n" +
                "几点云影，一道水，\n" +
                "\n" +
                "一条桥，一支橹声，\n" +
                "\n" +
                "一林松，一丛竹，\n" +
                "\n" +
                "红叶纷纷：\n" +
                "\n" +
                "艳色的田野，艳色的秋景，\n" +
                "\n" +
                "梦境似的分明，\n" +
                "\n" +
                "模糊，消隐——\n" +
                "\n" +
                "催催催！\n" +
                "\n" +
                "是车轮还是光阴？\n" +
                "\n" +
                "催老了秋容，\n" +
                "\n" +
                "催老了人生！"
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