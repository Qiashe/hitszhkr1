package com.example.hitszhkr1.webfuns

import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import com.example.hitszhkr1.interfaces.AsyncResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.log

open class AsyncGetJson : AsyncTask<URL,Int,String>() {

    public lateinit var asyncResponse : AsyncResponse
    public fun setOnAsyncResponse(asyncResponse: AsyncResponse){
        this.asyncResponse = asyncResponse
    }

    override fun doInBackground(vararg params: URL?): String {
        try {
            Log.d("web","Thread get json start , trying to load doc from" + params[0].toString())
            val response = StringBuilder()
            val url = params[0]
            val connection = url?.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 8000
            connection.readTimeout = 8000
            val input = connection.inputStream
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    response.append(it)
                }
            }
            Log.d("web", "Thread request json finished , result is $response")
            return response.toString()
        }catch (e : Exception){
            e.printStackTrace()
            return "NULL"
        }
    }

    override fun onPostExecute(result: String?) {
        if (result != null){
            asyncResponse.onDataReceivedSuccess(result)
        }else{
            asyncResponse.onDataReceivedFailed()
        }
        super.onPostExecute(result)
    }

}