package com.lbnkosi.weatherapp.data.mappers

import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData

fun LocalWeatherData.map(): WeatherDataEntity {
    return WeatherDataEntity(id, weatherData)
}

fun WeatherDataEntity.map(): LocalWeatherData {
    return LocalWeatherData(id, weatherData)
}