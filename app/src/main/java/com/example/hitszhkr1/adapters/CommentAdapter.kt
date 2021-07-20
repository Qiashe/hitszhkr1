package com.example.hitszhkr1.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hitszhkr1.activity.Comment
import com.example.hitszhkr1.R

class CommentAdapter(private val context: Context, private  val item_list : List<Comment>, private val activity: Activity) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val userId : TextView = view.findViewById(R.id.commentUserId)
        val commentContent : TextView = view.findViewById(R.id.commentContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentItem = item_list[position]
        if (commentItem.mark != -1.0)
            holder.userId.text = "UserID : " + commentItem.userId + " 评分 : " + commentItem.mark
        else
            holder.userId.text = "UserID : " + commentItem.userId + " 未评分"
        holder.commentContent.text = commentItem.comment
    }

    override fun getItemCount()=item_list.size

}
