package com.lbnkosi.weatherapp.data.network

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(msg: String?, errorType: ErrorType)
}