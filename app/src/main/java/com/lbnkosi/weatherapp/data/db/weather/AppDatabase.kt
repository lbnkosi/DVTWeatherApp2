package com.lbnkosi.weatherapp.data.db.weather

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.data.typeconverters.WeatherDataTypeConverter

@Database(entities = [WeatherDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(WeatherDataTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao

    companion object {
        const val DB_NAME = "WeatherApp.db"
    }

}