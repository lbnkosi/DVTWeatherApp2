package com.lbnkosi.weatherapp.data.db.places

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.data.typeconverters.WeatherDataTypeConverter

@Database(entities = [PlacesEntity::class], version = 1, exportSchema = false)
@TypeConverters(WeatherDataTypeConverter::class)
abstract class PlacesDatabase: RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {
        const val PLACES_DB = "Places.db"
    }

}