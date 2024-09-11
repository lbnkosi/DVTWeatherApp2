package com.lbnkosi.weatherapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lbnkosi.weatherapp.data.typeconverters.WeatherDataTypeConverter
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData

@Entity(tableName = "WeatherDataEntity")
class WeatherDataEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "weatherData")
    @TypeConverters(WeatherDataTypeConverter::class)
    var weatherData: WeatherData? = WeatherData()
)