package com.example.hitszhkr1.drawerActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hitszhkr1.R
import com.example.hitszhkr1.datafuns.Favor
import kotlinx.android.synthetic.main.activity_feedback.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        feedback_submit.setOnClickListener {
            val feedback=editText.text.toString()
            val type=1;
            if (feedback.isEmpty()){
                Toast.makeText(this, "请输入反馈！", Toast.LENGTH_SHORT).show()
            }
            else{
                val str =getString(R.string.url_post_feedback)+"type=$type"+"&content=$feedback"
                thread {
                    val url = URL(str)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.connectTimeout = 2000
                    if (connection.responseCode == 200){
                        Log.d("test","feedback succeed")
                        runOnUiThread{
                            Toast.makeText(this, "反馈已提交！", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }else{
                        Log.d("test","feedback failed , responseCode = ${connection.responseCode}")
                        Toast.makeText(this, "反馈提交失败！", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}