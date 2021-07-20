package com.example.hitszhkr1.webfuns

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import com.example.hitszhkr1.interfaces.AsyncImage
import java.net.HttpURLConnection
import java.net.URL

open class AsyncGetAllImage : AsyncTask<Int,Int,Bitmap>(){

    lateinit var asyncImage : AsyncImage
    fun setOnAsyncResponse(asyncImage: AsyncImage){
        this.asyncImage = asyncImage
    }

    override fun doInBackground(vararg params: Int?): Bitmap? {
        return try {
            Log.d("web", "Loading Image id = $params")
            val url2 = URL("http://1.15.152.95:8080/HITadmin/image/getImage?imgId=$params")
            val connection2 = url2.openConnection() as HttpURLConnection
            connection2.requestMethod = "GET"
            connection2.connectTimeout = 8000
            connection2.readTimeout = 8000
            var bitmap : Bitmap? = null
            val input2 = connection2.inputStream
            bitmap = BitmapFactory.decodeStream(input2)
            bitmap
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        if (result != null){
            asyncImage.onDataReceivedSuccess(result)
        }else{
            Log.d("test","bit map is null")
            asyncImage.onDataReceivedFailed()
        }
    }

}