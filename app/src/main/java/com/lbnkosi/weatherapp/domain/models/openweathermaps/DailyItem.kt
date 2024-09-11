package com.lbnkosi.weatherapp.domain.models.openweathermaps

import com.google.gson.annotations.SerializedName

data class DailyItem(
    @SerializedName("moonset")
    val moonset: Int = 0,
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("moon_phase")
    val moonPhase: Double = 0.0,
    @SerializedName("uvi")
    val uvi: Double = 0.0,
    @SerializedName("moonrise")
    val moonrise: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("clouds")
    val clouds: Int = 0,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike = FeelsLike(),
    @SerializedName("wind_gust")
    val windGust: Double = 0.0,
    @SerializedName("dt")
    val dt: Int = 0,
    @SerializedName("pop")
    val pop: Double = 0.0,
    @SerializedName("wind_deg")
    val windDeg: Int = 0,
    @SerializedName("dew_point")
    val dewPoint: Double = 0.0,
    @SerializedName("sunset")
    val sunset: Int = 0,
    @SerializedName("weather")
    val weather: List<WeatherItem>? = arrayListOf(),
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("wind_speed")
    val windSpeed: Double = 0.0
)