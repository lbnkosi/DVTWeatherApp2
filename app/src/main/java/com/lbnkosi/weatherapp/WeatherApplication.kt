package com.lbnkosi.weatherapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Places.initialize(applicationContext, "AIzaSyDp54F2JTJPsHH7ln0yzhnKi2PbY4BhQh0")
    }

}