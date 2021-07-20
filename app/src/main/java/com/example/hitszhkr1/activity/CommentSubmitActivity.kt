package com.example.hitszhkr1.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.hitszhkr1.R
import com.example.hitszhkr1.datafuns.Favor
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_canteen_item.*
import kotlinx.android.synthetic.main.activity_comment_submit.*
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CommentSubmitActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_submit)
        comment_cancel.setBackgroundColor(Color.TRANSPARENT)
        comment_submit.setBackgroundColor(Color.TRANSPARENT)

        val editor = getSharedPreferences("appdata",Context.MODE_PRIVATE)
        val userId = editor.getInt("userId",0)
        val title = intent.getStringExtra("name")
        val canteenItemId = intent.getIntExtra("canteenItemId",0)
        comment_title.text = "为${title}评价"
        comment_userId.text = "UserId:$userId"

        comment_cancel.setOnClickListener {
            finish()
        }

        comment_submit.setOnClickListener {
            val mark = comment_rate.rating
            val comment : String = comment.text.toString()
            if (mark <= 0 && comment.isEmpty()){
                Toast.makeText(this, "请输入评价！", Toast.LENGTH_SHORT).show()
            }
            else{
                val isLike = Favor(this).ifLiked(canteenItemId)
                val str = getString(R.string.url_post_review)+"userId=$userId"+"&canteenItemId=$canteenItemId"+"&content=$comment"+"&mark=$mark"+"&isLike=$isLike"
                thread {
                    val url = URL(str)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.connectTimeout = 2000
                    if (connection.responseCode == 200){
                        Log.d("test","post succeed")
                        runOnUiThread{
                            Toast.makeText(this, "评价已提交！", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }else{
                        Log.d("test","post failed , responseCode = ${connection.responseCode}")
                        Toast.makeText(this, "评价提交失败！", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

    }
}