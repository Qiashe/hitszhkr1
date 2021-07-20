package com.example.hitszhkr1.interfaces

interface AsyncResponse {
    fun onDataReceivedSuccess(data: String)
    fun onDataReceivedFailed()
}