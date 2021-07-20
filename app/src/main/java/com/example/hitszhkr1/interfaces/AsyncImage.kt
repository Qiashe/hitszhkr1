package com.example.hitszhkr1.interfaces

import android.graphics.Bitmap

interface AsyncImage {
    fun onDataReceivedSuccess(data:Bitmap)
    fun onDataReceivedFailed()
}