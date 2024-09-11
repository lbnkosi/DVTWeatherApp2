package com.lbnkosi.weatherapp.features.utils

import androidx.compose.ui.graphics.Color
import com.lbnkosi.weatherapp.R
import com.lbnkosi.weatherapp.domain.enums.WeatherType
import com.lbnkosi.weatherapp.domain.models.weatherdata.DailyWeatherData
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.features.utils.Utility.getDayOfWeekFromTimestamp
import com.lbnkosi.weatherapp.features.utils.Utility.getWeatherType
import com.lbnkosi.weatherapp.features.utils.Utility.kelvinToCelsius

fun WeatherType.getBackgroundImage(): Int {
    return if (this == WeatherType.Sunny) {
        R.drawable.forest_sunny
    } else if (this == WeatherType.Rainy) {
        R.drawable.forest_rainy
    } else {
        R.drawable.forest_cloudy
    }
}

fun WeatherType.backgroundColour(): Color {
    return if (this == WeatherType.Sunny) {
        Color(0xFF47AB2F)
    } else if (this == WeatherType.Rainy) {
        Color(0xFF57575D)
    } else {
        Color(0xFF54717A)
    }
}

fun WeatherType.weatherIcon(): Int {
    return if (this == WeatherType.Sunny) {
        R.drawable.clear
    } else if (this == WeatherType.Rainy) {
        R.drawable.rain
    } else {
        R.drawable.partlysunny
    }
}

fun OpenWeatherMapResponse.mapResponseToWeatherData(lat: Double? = 0.0, lon: Double? = 0.0): WeatherData {
    val dailyWeatherDataList: MutableList<DailyWeatherData> = arrayListOf()
    val weatherData = WeatherData()

    this.daily?.forEach {
        val weather = it.weather?.first()
        val weatherType = getWeatherType(mapOf("id" to weather?.id, "main" to weather?.main, "description" to weather?.description, "icon" to weather?.icon))
        dailyWeatherDataList.add(DailyWeatherData(it.dt.getDayOfWeekFromTimestamp(), it.temp.day.kelvinToCelsius(), weatherType))
    }

    val currentWeather = this.current
    val weather = currentWeather.weather?.first()
    weatherData.dailyWeatherData = dailyWeatherDataList
    weatherData.maxTemperature = currentWeather.feelsLike.kelvinToCelsius()
    weatherData.minTemperature = currentWeather.dewPoint.kelvinToCelsius()
    weatherData.currentTemperature = currentWeather.temp.kelvinToCelsius()
    weatherData.weatherType = getWeatherType(mapOf("id" to weather?.id, "main" to weather?.main, "description" to weather?.description, "icon" to weather?.icon))
    weatherData.lat = lat
    weatherData.lon = lon
    return weatherData
}
