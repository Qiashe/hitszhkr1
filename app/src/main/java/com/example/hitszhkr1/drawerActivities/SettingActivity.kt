package com.example.hitszhkr1.drawerActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hitszhkr1.R
import com.example.hitszhkr1.interfaces.AsyncResponse
import com.example.hitszhkr1.webfuns.AsyncGetJson
import kotlinx.android.synthetic.main.activity_setting.*
import java.lang.Exception
import java.net.URL

class SettingActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar_setting.title="设置"
        setSupportActionBar(toolbar_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            val asyncGetJson = AsyncGetJson()
            asyncGetJson.execute(URL(getString(R.string.url_get_version)))
            try {
                asyncGetJson.setOnAsyncResponse(object : AsyncResponse{
                    override fun onDataReceivedSuccess(data: String) {
                        if (data.equals(textSettingUpdateText)){
                            Toast.makeText(this@SettingActivity,"当前已是最新版本",Toast.LENGTH_SHORT)
                        }
                        else{
                            Thread.sleep(1000)
                            val intent=Intent(Intent.ACTION_VIEW)
                            intent.data=Uri.parse("https://gitee.com/hitsz-hitasdev/hitsz-as/releases")
                            startActivity(intent)
                        }
                    }

                    override fun onDataReceivedFailed() {
                        Log.d("web","Fail when loading version json data")
                    }
                })
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        //第二个组件，反馈BUG
        text_bg_title.text=textSettingBgTitle
        text_bg_hint.text=textSettingBgHint
        bg_button.setBackgroundColor(Color.TRANSPARENT)
        bg_button.setOnClickListener {
            val intent=Intent(this,FeedbackActivity::class.java)
            startActivity(intent)
        }

        //第三个组件，清除本地数据
        text_clear_title.text=textSettingClearTitle
        text_clear_hint.text=textSettingClearHint
        clear_button.setBackgroundColor(Color.TRANSPARENT)
        clear_button.setOnClickListener {
            Toast.makeText(this, "已清除", Toast.LENGTH_SHORT).show()
            //删除数据库数据
        }

        text_goto_git.text="访问项目Gitee地址"
        goto_git.setBackgroundColor(Color.TRANSPARENT)
        goto_git.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW)
            intent.data=Uri.parse("https://gitee.com/hitsz-hitasdev/hitsz-as")
            startActivity(intent)
        }

        help.text="·我们的数据来源\n" +
                "此软件所有数据皆为用户自发上传，经筛选得到。任何人都可以上传评论/评分。\n" +
                "·如何更新软件数据\"\n" +
                "请按照“关于”中的联系方式联系我们，我们将修改数据。"

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