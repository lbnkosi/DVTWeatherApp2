package com.lbnkosi.weatherapp.domain.models.places

import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData

data class Place(
    var id: Int = 0,
    var lat: String = "",
    var long: String = "",
    var name: String? = "",
    var description: String? = "",
    var created: String = "",
    var weatherData: WeatherData? = WeatherData()
)