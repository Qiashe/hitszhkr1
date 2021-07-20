package com.example.hitszhkr1.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CommentAdapter
import com.example.hitszhkr1.datafuns.Favor
import com.example.hitszhkr1.interfaces.AsyncImage
import com.example.hitszhkr1.interfaces.AsyncResponse
import com.example.hitszhkr1.objects.CanteenItem
import com.example.hitszhkr1.popup.MarkPopupWindow
import com.example.hitszhkr1.webfuns.AsyncGetJson
import com.example.hitszhkr1.webfuns.AsyncGetSingleImage
import kotlinx.android.synthetic.main.activity_canteen_item.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.canteen_item.*
import org.json.JSONArray
import java.net.URL
import kotlin.math.min


class CanteenItemActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")

    private lateinit var popupWindow:PopupWindow
    private val asyncGetSingleImage = AsyncGetSingleImage()
    private var likeStatus = 0
    private val commentList = ArrayList<Comment>()
    private lateinit var jsonData : String

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canteen_item)
        setSupportActionBar(toolbar_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapse_item.title = " "

//        setStatusBarFullTransparent()

        //接收传入的信息
        val canteenItemId=intent.getIntExtra("canteenItemId",0)
        val name=intent.getStringExtra("name")
        val facilityItemId=intent.getIntExtra("facilityItemId",0)
        val info=intent.getStringExtra("info")
        val price=intent.getDoubleExtra("price", 0.0)
        val spicyRate=intent.getDoubleExtra("spicyRate",0.0)
        val sweetRate=intent.getDoubleExtra("sweetRate",0.0)
        val mark=intent.getDoubleExtra("mark",0.0)
        val canteenName=intent.getStringExtra("canteenName")
        val avePrice=intent.getDoubleExtra("avePrice",0.00)
        val aveMark=intent.getDoubleExtra("aveMark",0.00)
        val avePriceStr = String.format("%.2f",avePrice)
        val aveMarkStr = String.format("%.2f",aveMark)

        canteenItemTitle.text = name+"概述"
        canteenItemPrice.text = "$price 元"
        canteenItemCanteen.text = canteenName
        canteenItemAveMark.text = aveMarkStr
        canteenItemAvePrice.text = avePriceStr
        canteenItemMark.text = mark.toString()
        canteenItemSpicyRate.text = spicyRate.toString()
        canteenItemSweetRate.text = sweetRate.toString()
        canteenItemInfo.text = info

        canteenItemSubmitReview.setBackgroundColor(Color.TRANSPARENT)
        canteenItemLikeButton.setBackgroundColor(Color.TRANSPARENT)
        canteenItemLoadReview.setBackgroundColor(Color.TRANSPARENT)
        canteenItemLoadPicture.setBackgroundColor(Color.TRANSPARENT)

        webGetImage(name)
        webGetComment(canteenItemId)

        //创建数据对象
        val canteenItem = CanteenItem(canteenItemId,name,facilityItemId,info,price,spicyRate,sweetRate,mark)

        likeStatus = Favor(this).ifLiked(canteenItemId)

        when(likeStatus){
            1 -> {
                canteenItemLikeButton.setImageResource(R.drawable.ic_like_filled)
            }
        }
        canteenItemLikeButton.setOnClickListener {
            when (likeStatus) {
                1 -> {
                    //删数据
                    likeStatus = Favor(this).likeCancel(canteenItemId)
                    canteenItemLikeButton.setImageResource(R.drawable.ic_like)
                    Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show()
                }
                0 -> {
                    //添加记录
                    val value = ContentValues().apply {
                        put("canteenItemId", canteenItem.canteenItemId)
                        put("facilityItemId", canteenItem.facilityItemId)
                        put("info", canteenItem.info)
                        put("mark", canteenItem.mark)
                        put("name", canteenItem.name)
                        put("price", canteenItem.price)
                        put("spicyRate", canteenItem.spicyRate)
                        put("sweetRate", canteenItem.sweetRate)
                    }
                    likeStatus = Favor(this).likeInsert(value)
                    canteenItemLikeButton.setImageResource(R.drawable.ic_like_filled)
                    Toast.makeText(this, "已收藏！", Toast.LENGTH_SHORT).show()
                }
                -1 -> {
                    Log.d("database","wrong in likeStatus")
                }
            }
        }

        //提交评价按钮监听
        canteenItemSubmitReview.setOnClickListener {
            val intent = Intent(this,CommentSubmitActivity::class.java).apply {
                putExtra("name",name)
                putExtra("canteenItemId",canteenItemId)
            }
            startActivity(intent)
        }

        //点击查看更多图片按钮监听
        canteenItemLoadPicture.setOnClickListener {
            val intent=Intent(this, MorePicturesActivity::class.java).apply {
                putExtra("canteenItemId",canteenItemId)
                putExtra("name",name)
            }
            this.startActivity(intent)
        }

        //点击查看全部评论按钮监听
        canteenItemLoadReview.setOnClickListener {
            try {
                val intent=Intent(this,CommentActivity::class.java).apply {
                    putExtra("jsonData",jsonData)
                    putExtra("name",name)
                }
                this.startActivity(intent)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        asyncGetSingleImage.cancel(true)
    }

    //监听返回按钮
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 联网向服务器请求头图
     */
    private fun webGetImage(name : String){
        asyncGetSingleImage.execute(URL(getString(R.string.url_get_canteen_image_list)+name))
        asyncGetSingleImage.setOnAsyncResponse(object  : AsyncImage{
            override fun onDataReceivedSuccess(data: Bitmap) {
                runOnUiThread{
                    Glide.with(this@CanteenItemActivity).load(data).into(image_item)
                }
            }
            override fun onDataReceivedFailed() {
                Log.d("web","Fail when loading image json data")
            }
        })
    }

    /**
     * 联网向服务器请求评论
     * 此处向服务器请求的json数据将会传递给查看评论Activity
     */
    private fun webGetComment(canteenItemId : Int){
        val asyncGetJson = AsyncGetJson()
        asyncGetJson.execute(URL(getString(R.string.url_get_canteen_comment)+canteenItemId))
        try {
            asyncGetJson.setOnAsyncResponse(object : AsyncResponse {
                override fun onDataReceivedSuccess(data: String) {
                    Log.d("test","data = $data")
                    jsonData = data
                    val jsonArray = JSONArray(data)
                    val commentNum = min(3,jsonArray.length())
                    for (i in 0 until commentNum){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userId = jsonObject.getInt("userId")
                        val content = jsonObject.getString("content")
                        val mark = jsonObject.getDouble("mark")
                        val item = Comment(userId,content,mark)
                        commentList.add(item)
                    }
                    runOnUiThread {
                        val layoutManager = LinearLayoutManager(this@CanteenItemActivity)
                        commentRecycler.layoutManager = layoutManager
                        val adapter = CommentAdapter(this@CanteenItemActivity,commentList,activity = CanteenItemActivity())
                        commentRecycler.adapter = adapter
                    //TODO
                    }
                }

                override fun onDataReceivedFailed() {
                    Log.d("web","Fail when loading comment json data")
                }
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

}

public class Comment(val userId : Int , val comment : String , val mark : Double)