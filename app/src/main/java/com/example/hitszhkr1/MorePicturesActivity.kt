package com.example.hitszhkr1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hitszhkr1.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_more_pictures.*

class MorePicturesActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_pictures)
        val itemID=intent.getIntExtra("ID",1)
        val db=dbHelper.readableDatabase

        var itemName = "NAME"

        val cursor = db.query("Canteen",null,null,null,null,null,null)
        if (cursor.moveToFirst()) {
            do {
                val getID=cursor.getInt(cursor.getColumnIndex("id"))
                if (getID == itemID){
                    itemName=cursor.getString(cursor.getColumnIndex("name"))
                }
            }while (cursor.moveToNext())
        }
        cursor.close()

        button_back.setOnClickListener {
            finish()
        }

        itemName += "的图片"
        item_title.text = itemName
        button_back.setBackgroundColor(Color.TRANSPARENT)

    }
}