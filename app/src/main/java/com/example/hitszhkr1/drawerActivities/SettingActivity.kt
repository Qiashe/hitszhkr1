package com.example.hitszhkr1.drawerActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hitszhkr1.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapse_setting.title="设置"


        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName,0)
        val code = info.versionName
        val textSettingUpdateTitle="软件更新"   //软件更新控件左侧标题
        val textSettingUpdateText="当前软件版本 v$code" //软件更新控件右侧，版本
        val textSettingBgTitle="反馈问题"   //bug反馈控件左侧标题
        val textSettingBgHint="点此反馈"    //bug反馈控件右侧hint
        val textSettingClearTitle="清除本地缓存"  //清除缓存控件左侧标题
        val textSettingClearHint="点此清除"  //清除缓存控件右侧hint


        //写组件信息
        //第一个组件，更新软件版本
        text_setting_update_title.text=textSettingUpdateTitle
        text_setting_update_version.text=textSettingUpdateText
        update_button.setBackgroundColor(Color.TRANSPARENT) //按钮设为透明
        update_button.setOnClickListener {
            Toast.makeText(this, "正在检查更新", Toast.LENGTH_SHORT).show()
            //向服务端请求数据!!
        }

        //第二个组件，反馈BUG
        text_bg_title.text=textSettingBgTitle
        text_bg_hint.text=textSettingBgHint
        bg_button.setBackgroundColor(Color.TRANSPARENT)
        bg_button.setOnClickListener {
            //写问题反馈Activity!
        }

        //第三个组件，清除本地数据
        text_clear_title.text=textSettingClearTitle
        text_clear_hint.text=textSettingClearHint
        clear_button.setBackgroundColor(Color.TRANSPARENT)
        clear_button.setOnClickListener {
            Toast.makeText(this, "已清除", Toast.LENGTH_SHORT).show()
            //删除数据库数据
        }

        //第四个组件，开发者模式
        text_dev_title.text="Developer Mode"
        text_dev_hint.text=" "
        dev_button.setBackgroundColor(Color.TRANSPARENT)
        dev_button.setOnClickListener {
            Toast.makeText(this, "opening", Toast.LENGTH_SHORT).show()
            val intent=Intent(this,DeveloperModeActivity::class.java)
            startActivity(intent)
        }
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