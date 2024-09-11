package com.lbnkosi.weatherapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lbnkosi.weatherapp.data.typeconverters.WeatherDataTypeConverter
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData

@Entity(tableName = "Places")
class PlacesEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name= "lat")
    var lat: String = "",

    @ColumnInfo(name= "long")
    var long: String = "",

    @ColumnInfo(name= "name")
    var name: String? = "",

    @ColumnInfo(name= "description")
    var description: String? = "",

    @ColumnInfo(name= "created")
    var created: String = "",

    @ColumnInfo(name = "weatherData")
    @TypeConverters(WeatherDataTypeConverter::class)
    var weatherData: WeatherData? = WeatherData()
)