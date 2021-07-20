package com.example.hitszhkr1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment.*
import org.json.JSONArray
import kotlin.math.min

class CommentActivity : AppCompatActivity() {

    private val commentList = ArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        val name = intent.getStringExtra("name")
        toolbar_comments.title = "${name}的全部评论"
        setSupportActionBar(toolbar_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val jsonData = intent.getStringExtra("jsonData")
        val jsonArray = JSONArray(jsonData)
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val userId = jsonObject.getInt("userId")
            val content = jsonObject.getString("content")
            val mark = jsonObject.getDouble("mark")
            val item = Comment(userId,content,mark)
            commentList.add(item)
        }
        val layoutManager = LinearLayoutManager(this)
        commentsRecycler.layoutManager = layoutManager
        val adapter = CommentAdapter(this,commentList,activity = CommentActivity())
        commentsRecycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}