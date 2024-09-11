package com.lbnkosi.weatherapp.domain.models.openweathermaps

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("main")
    val main: String = "",
    @SerializedName("id")
    val id: Int = 0
)