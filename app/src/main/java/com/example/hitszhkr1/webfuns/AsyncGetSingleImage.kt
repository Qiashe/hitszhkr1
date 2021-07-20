package com.example.hitszhkr1.webfuns

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import com.example.hitszhkr1.interfaces.AsyncImage
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

open class AsyncGetSingleImage : AsyncTask<URL,Int,Bitmap>(){

    lateinit var asyncImage : AsyncImage
    fun setOnAsyncResponse(asyncImage: AsyncImage){
        this.asyncImage = asyncImage
    }

    override fun doInBackground(vararg params: URL?): Bitmap? {
        return try {
            val response1 = StringBuilder()
            val url1 = params[0]
            val connection1 = url1?.openConnection() as HttpURLConnection
            connection1.requestMethod = "POST"
            connection1.connectTimeout = 8000
            connection1.readTimeout = 8000
            val input1 = connection1.inputStream
            val reader = BufferedReader(InputStreamReader(input1))
            reader.use{
                reader.forEachLine {
                    response1.append(it)
                }
            }
            val jsonReceive = response1.toString()

            val jsonArray = JSONArray(jsonReceive)
            val jsonObject = jsonArray.getJSONObject(0)
            val imageId = jsonObject.getInt("imageId")

            val url2 = URL("http://1.15.152.95:8080/HITadmin/image/getImage?imgId=$imageId")
            Log.d("web","Loading Image id = $imageId")
            val connection2 = url2.openConnection() as HttpURLConnection
            connection2.requestMethod = "GET"
            connection2.connectTimeout = 8000
            connection2.readTimeout = 8000
            var bitmap : Bitmap? = null
            bitmap = BitmapFactory.decodeStream(connection2.inputStream)
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
            Log.d("web","Bitmap is null")
            asyncImage.onDataReceivedFailed()
        }
    }

}