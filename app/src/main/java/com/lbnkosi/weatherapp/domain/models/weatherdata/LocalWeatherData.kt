package com.lbnkosi.weatherapp.domain.models.weatherdata

data class LocalWeatherData(
    var id: Int = 0,
    var weatherData: WeatherData? = WeatherData()
)
