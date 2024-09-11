package com.lbnkosi.weatherapp.domain.models.openweathermaps

import com.google.gson.annotations.SerializedName

data class OpenWeatherMapResponse(
    @SerializedName("current")
    val current: Current = Current(),
    @SerializedName("timezone")
    val timezone: String = "",
    @SerializedName("timezone_offset")
    val timezoneOffset: Int = 0,
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("daily")
    val daily: List<DailyItem>? = arrayListOf(),
    @SerializedName("hourly")
    val hourly: List<HourlyItem>? = arrayListOf()
)