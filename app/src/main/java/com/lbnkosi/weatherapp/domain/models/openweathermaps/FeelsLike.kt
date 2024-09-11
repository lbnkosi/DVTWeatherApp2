package com.lbnkosi.weatherapp.domain.models.openweathermaps

import com.google.gson.annotations.SerializedName

data class FeelsLike(
    @SerializedName("eve")
    val eve: Double = 0.0,
    @SerializedName("night")
    val night: Double = 0.0,
    @SerializedName("day")
    val day: Double = 0.0,
    @SerializedName("morn")
    val morn: Double = 0.0
)