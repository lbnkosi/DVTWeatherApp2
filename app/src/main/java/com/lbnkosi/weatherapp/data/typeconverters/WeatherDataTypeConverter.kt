package com.lbnkosi.weatherapp.data.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import java.lang.reflect.Type

class WeatherDataTypeConverter {

    private var gson: Gson = Gson()

    @TypeConverter
    fun stringToWeatherData(data: String?): WeatherData {
        if (data == null) {
            return WeatherData()
        }
        val type: Type = object : TypeToken<WeatherData>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun weatherDataToString(weatherData: WeatherData): String? {
        return gson.toJson(weatherData)
    }

}