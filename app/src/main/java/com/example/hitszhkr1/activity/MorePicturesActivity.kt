package com.example.hitszhkr1.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.ImageListAdapter
import com.example.hitszhkr1.interfaces.AsyncResponse
import com.example.hitszhkr1.webfuns.AsyncGetJson
import kotlinx.android.synthetic.main.activity_more_pictures.*
import org.json.JSONArray
import java.net.URL

class MorePicturesActivity : AppCompatActivity() {

    var getJsonData = "Null"
    var imageIdList = ArrayList<Int>()
    private val asyncGetJson0 = AsyncGetJson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_pictures)
        val name = intent.getStringExtra("name")
        toolbar_pictures.title = "${name}的图片"
        setSupportActionBar(toolbar_pictures)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        asyncGetJson0.execute(URL("http://1.15.152.95:8080/HITadmin/canteenItem/findImg?name=$name"))

        //加载图片列表
        try {
            asyncGetJson0.setOnAsyncResponse(object : AsyncResponse {
                override fun onDataReceivedSuccess(data: String) {
                    getJsonData = data
                    createImageIdList(data)
                    runOnUiThread {
                        val layoutManager = LinearLayoutManager(this@MorePicturesActivity)
                        images_recycler.layoutManager = layoutManager
                        val adapter = ImageListAdapter(imageIdList,this@MorePicturesActivity)
                        images_recycler.adapter = adapter
                    }
                }

                override fun onDataReceivedFailed() {
                    Log.d("web","Data Receive Fail")
                }

            })
        }catch (e:Exception){
            e.printStackTrace()
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

    override fun onStop() {
        super.onStop()
        asyncGetJson0.cancel(true)
    }

    private fun createImageIdList(jsonData : String){
        val jsonArray = JSONArray(jsonData)
        for (i in 0 until  jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val imageId = jsonObject.getInt("imageId")
            imageIdList.add(imageId)
            Log.d("test","get imageID is $imageId")
        }
    }

}