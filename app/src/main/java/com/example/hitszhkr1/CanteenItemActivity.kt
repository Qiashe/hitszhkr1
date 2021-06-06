package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RatingBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hitszhkr1.database.DatabaseHelper
import com.example.hitszhkr1.popup.MarkPopupWindow
import kotlinx.android.synthetic.main.activity_canteen_item.*

class CanteenItemActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private val createIndex="create table Canteen ("+
            "id integer primary key autoincrement," +
            "name text," +
            "mark integer," +
            "spice integer," +
            "sweet integer," +
            "location text," +
            "canteenNum integer," +
            "reviewNum integer," +
            "imageID integer," +
            "price real," +
            "about text)"
    private val dbVersion=1
    private val dbHelper= DatabaseHelper(this,"canteen.db",createIndex,dbVersion)
    private lateinit var popupWindow:PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canteen_item)
        val idGet=intent.getIntExtra("ID",0) //接受ID

        val imageGet=intent.getIntExtra("ImageId",0) //Image ID Receiver
        Toast.makeText(this, "ImageIDis$imageGet", Toast.LENGTH_SHORT).show()
        val db=dbHelper.writableDatabase
        var itemName="Default"
        var itemAbout="Default"
        var label2="Default"
        var canteenNum=0
        var spice=0
        var itemPrice= 0.00
        var reviewNumber= 120
        var itemMark=0

        val cursor = db.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                if (id == idGet){
                    itemAbout=cursor.getString(cursor.getColumnIndex("about"))
                    itemName=cursor.getString(cursor.getColumnIndex("name"))
                    label2=cursor.getString(cursor.getColumnIndex("location"))
                    itemPrice=cursor.getDouble(cursor.getColumnIndex("price"))
                    reviewNumber=cursor.getInt(cursor.getColumnIndex("reviewNum"))
                    canteenNum=cursor.getInt(cursor.getColumnIndex("canteenNum"))
                    itemMark=cursor.getInt(cursor.getColumnIndex("mark"))
                    spice=cursor.getInt(cursor.getColumnIndex("spice"))
                }
            }while (cursor.moveToNext())
        }
        cursor.close()

        val itemTitle= "关于$itemName"        //勿动
        val reviewText="$reviewNumber 评价"   //勿动
        val label1="辛辣"                     //勿动
        val pictureTitle="$itemName 的图片"   //勿动

        button_see_more.setBackgroundColor(Color.TRANSPARENT)
        setSupportActionBar(toolbar_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        name_of_item.text=itemName
        collapse_item.title=" "
        mark_text.text=reviewText
        label_text1.text=label1
        label_text2.text=label2
        item_about_title.text=itemTitle
        item_about_text.text=itemAbout
        price_of_item.text="$itemPrice"
        picture_about_title.text=pictureTitle

        when(spice){    //是否辛辣——icon加载
            0 -> Glide.with(this).load(R.drawable.ic_spice_no).into(label_icon1)
            else -> Glide.with(this).load(R.drawable.ic_spice_yes).into(label_icon1)
        }

        when(canteenNum){   //食堂号——icon加载
            0 -> Glide.with(this).load(R.drawable.ic_question).into(label_icon2)
            1 -> Glide.with(this).load(R.drawable.ic_num_1).into(label_icon2)
            2 -> Glide.with(this).load(R.drawable.ic_num_2).into(label_icon2)
            3 -> Glide.with(this).load(R.drawable.ic_num_3).into(label_icon2)
            4 -> Glide.with(this).load(R.drawable.ic_num_4).into(label_icon2)
            else -> Glide.with(this).load(R.drawable.ic_question).into(label_icon2)
        }

        when(itemMark){     //评分——icon加载
            0 -> Glide.with(this).load(R.drawable.ic_question).into(star_mark)
            1 -> Glide.with(this).load(R.drawable.ic_star_1).into(star_mark)
            2 -> Glide.with(this).load(R.drawable.ic_star_2).into(star_mark)
            3 -> Glide.with(this).load(R.drawable.ic_star_3).into(star_mark)
            4 -> Glide.with(this).load(R.drawable.ic_star_4).into(star_mark)
            5 -> Glide.with(this).load(R.drawable.ic_star_5).into(star_mark)
            else -> Glide.with(this).load(R.drawable.ic_question).into(star_mark)
        }

        Glide.with(this).load(imageGet).into(image_item)
        Glide.with(this).load(imageGet).into(picture_about1)
        Glide.with(this).load(R.drawable.no_image).into(picture_about2)
        Glide.with(this).load(R.drawable.no_image).into(picture_about3)
        Glide.with(this).load(R.drawable.no_image).into(picture_about4)
        Glide.with(this).load(R.drawable.no_image).into(picture_about5)
        Glide.with(this).load(R.drawable.no_image).into(picture_about6)

        val popViewParent= LayoutInflater.from(this).inflate(R.layout.activity_canteen_item,null)
        val popView= LayoutInflater.from(this,).inflate(R.layout.popup_mark,null)
        popupWindow= MarkPopupWindow(this,popView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isFocusable=true
        popupWindow.isTouchable=true
        popupWindow.animationStyle=R.style.BottomPopupTheme

        var rate = 0.00
        val contentView=popupWindow.contentView
        val markSubmitButton=contentView.findViewById<Button>(R.id.mark_submit_button)
        val rateBar=contentView.findViewById<RatingBar>(R.id.rate_bar_canteen)
        val markBackButton=contentView.findViewById<Button>(R.id.mark_back_button)
        mark_button.setOnClickListener {
            popupWindow.showAtLocation(popViewParent, Gravity.BOTTOM,0,0)
            rateBar.rating= 0F
        }
        markSubmitButton.setOnClickListener {
            rate = rateBar.rating.toDouble()
            Toast.makeText(this, "$rate", Toast.LENGTH_SHORT).show()
            //发送评分至服务器！
            popupWindow.dismiss()
        }
        markBackButton.setOnClickListener {
            popupWindow.dismiss()
        }

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