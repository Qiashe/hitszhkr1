package com.example.hitszhkr1.drawerActivities

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hitszhkr1.R
import com.example.hitszhkr1.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_developer_mode.*
import kotlinx.android.synthetic.main.activity_setting.*

class DeveloperModeActivity : AppCompatActivity() {

    private val createIndex="create table Canteen ("+
            "id integer primary key autoincrement," +
            "name text," +
            "mark integer," +
            "spice integer," +
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
        setContentView(R.layout.activity_developer_mode)
        dev_mode_button.setOnClickListener {
            val db=dbHelper.writableDatabase
            db.execSQL("drop table if exists Canteen")
            Toast.makeText(this, "Canteen table deleted", Toast.LENGTH_SHORT).show()
        }
        add_data_to_database.setOnClickListener {
            addToCanteenBook()
            Toast.makeText(this, "data added", Toast.LENGTH_SHORT).show()
        }

        delete_all_data.setOnClickListener {
            val db=dbHelper.writableDatabase
            db.delete("Canteen","id > ?", arrayOf("0"))
            Toast.makeText(this, "data deleted", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addToCanteenBook(){ //填充数据库中的数据（临时）
        val db=dbHelper.writableDatabase
        val value1 = ContentValues().apply {
            put("name","野菇鸡肉串")
            put("about","加工材料:蘑菇*1肉*1\n" +
                    "\n" +
                    "描述:由野菇和禽肉组成的烤串。鲜嫩禽肉佐以鲜香野菇，吃的时候也不要挑食哦。\n" +
                    "\n" +
                    "使用效果:为选中的角色恢复生命值上限的9%，并额外恢复1000点生命值。\n" +
                    "\n" +
                    "获得方式:成功烹饪野菇鸡肉串;猎鹿人购买;万民堂购买;")
            put("location","蒙德")
            put("canteenNum",1)
            put("mark",1)
            put("reviewNum",233)
            put("price",1.99)
            put("spice",0)
            put("imageID",R.drawable.pic_temp_1)
        }
        db.insert("Canteen",null,value1)
        val value2 = ContentValues().apply {
            put("name","甜甜花酿鸡")
            put("about"," 烹饪配方\n" +
                    "\n" +
                    "甜甜花*1+禽肉*1=甜甜花酿鸡(2星)\n" +
                    "\n" +
                    "食用效果\n" +
                    "\n" +
                    "使用后为选中的角色恢复900～1500点生命值，无CD时间，熟练满值20，暂无加成角色。\n" +
                    "\n" +
                    "十分推荐制作的即时恢复类料理，恢复数值很高。甜甜花在野外比较常见甚至比蘑菇更多，无论前期、中期还是后期都用处很大。 ")
            put("location","蒙德")
            put("canteenNum",2)
            put("mark",3)
            put("reviewNum",120)
            put("price",3.99)
            put("spice",0)
            put("imageID",R.drawable.pic_temp_2)
        }
        db.insert("Canteen",null,value2)
        val value3 = ContentValues().apply {
            put("name","蒙德土豆饼")
            put("about","星级:★★★\n" +
                    "\n" +
                    "加工材料:松果*2土豆*1果酱*1\n" +
                    "\n" +
                    "描述:土豆做的炸饼。据说没有果酱的土豆饼不能被冠上蒙德之名，而这两块的情况.大概连士豆饼都不能叫了?\n" +
                    "\n" +
                    "使用效果:为选中的角色恢复生命值上限的30%，并额外恢复600点生命值。\n" +
                    "\n" +
                    "获得方式:完成烹饪蒙德土豆饼\n" +
                    "\n" +
                    "食谱获得:试炼:烈焰与雷电通关获得")
            put("location","蒙德")
            put("canteenNum",4)
            put("mark",3)
            put("reviewNum",319)
            put("price",9.99)
            put("spice",0)
            put("imageID",R.drawable.pic_temp_3)
        }
        db.insert("Canteen",null,value3)
        val value4 = ContentValues().apply {
            put("name","提瓦特煎蛋")
            put("about","單面煎熟的禽蛋。\n" +
                    "蛋黃一戳就可以流出汁，非常適合解饞。")
            put("location","提瓦特")
            put("canteenNum",3)
            put("mark",1)
            put("reviewNum",23)
            put("price",0.99)
            put("spice",0)
            put("imageID",R.drawable.pic_temp_4)
        }
        db.insert("Canteen",null,value4)
        val value5 = ContentValues().apply {
            put("name","北地烟熏鸡")
            put("about","蔬菜搭配熏制好的禽肉。事实证明招待别人前先尝一口是很有必要的，比如现在重做一份还来得及。")
            put("location","璃月")
            put("canteenNum",1)
            put("mark",2)
            put("reviewNum",23)
            put("price",12.99)
            put("spice",1)
            put("imageID",R.drawable.pic_temp_5)
        }
        db.insert("Canteen",null,value5)
    }


}