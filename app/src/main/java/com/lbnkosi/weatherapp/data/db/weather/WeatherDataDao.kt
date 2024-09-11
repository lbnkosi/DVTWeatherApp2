package com.lbnkosi.weatherapp.data.db.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity

@Dao
interface WeatherDataDao {

    @Query("DELETE FROM WeatherDataEntity")
    suspend fun deleteWeatherData()

    @Query("SELECT * FROM WeatherDataEntity LIMIT 1")
    suspend fun getWeatherDataEntity(): WeatherDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createWeatherData(weatherDataEntity: WeatherDataEntity)

}