package com.lbnkosi.weatherapp.domain.models.weatherdata

import com.lbnkosi.weatherapp.domain.enums.WeatherType

data class DailyWeatherData (
    val day: String = "",
    val temperature: Int = 0,
    val weatherType: WeatherType = WeatherType.Sunny
)