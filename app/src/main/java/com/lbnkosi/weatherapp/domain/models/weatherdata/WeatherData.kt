package com.lbnkosi.weatherapp.domain.models.weatherdata

import com.lbnkosi.weatherapp.domain.enums.WeatherType

data class WeatherData(
    var currentTemperature: Int = 0,
    var minTemperature: Int = 0,
    var maxTemperature: Int = 0,
    var weatherType: WeatherType = WeatherType.Sunny,
    var dailyWeatherData: List<DailyWeatherData> = arrayListOf(),
    var lat: Double? = 0.0,
    var lon: Double? = 0.0,
    var usingCache: Boolean? = false
)