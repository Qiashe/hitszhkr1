package com.example.hitszhkr1

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.hitszhkr1.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_canteen_item.*

class CanteenItemActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private val createIndex="create table Canteen ("+
            "id integer primary key autoincrement," +
            "name text," +
            "mark integer," +
            "spice integer," +
            "location text," +
            "canteenNum integer," +
            "about text)"
    private val dbVersion=1
    private val dbHelper= DatabaseHelper(this,"canteen.db",createIndex,dbVersion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canteen_item)
        val idGet=intent.getIntExtra("ID",0) //接受ID
//        Toast.makeText(this, "IDis$id", Toast.LENGTH_SHORT).show()
        val db=dbHelper.writableDatabase
        var itemName="Default"
        var itemAbout="Default"

        val cursor = db.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val id=cursor.getInt(cursor.getColumnIndex("id"))
                if (id == idGet){
                    itemName=cursor.getString(cursor.getColumnIndex("name"))
                    itemAbout=cursor.getString(cursor.getColumnIndex("about"))
                }
            }while (cursor.moveToNext())
        }
        cursor.close()

        val itemTitle= "关于$itemName"
        val itemPrice= 9.99
        val reviewNumber= 120
        val reviewText="$reviewNumber 评价"
        val label1="辛辣"
        val label2="梨园"
        val pictureTitle="$itemName 的图片"
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
        Glide.with(this).load(R.drawable.tempstar1).into(star_mark)
        Glide.with(this).load(R.drawable.chicken).into(image_item)
        Glide.with(this).load(R.drawable.no).into(label_icon1)
        Glide.with(this).load(R.drawable.liyuan1).into(label_icon2)
        Glide.with(this).load(R.drawable.chicken).into(picture_about1)
        Glide.with(this).load(R.drawable.chicken).into(picture_about2)
        Glide.with(this).load(R.drawable.chicken).into(picture_about3)
        Glide.with(this).load(R.drawable.chicken).into(picture_about4)
        Glide.with(this).load(R.drawable.chicken).into(picture_about5)
        Glide.with(this).load(R.drawable.chicken).into(picture_about6)
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