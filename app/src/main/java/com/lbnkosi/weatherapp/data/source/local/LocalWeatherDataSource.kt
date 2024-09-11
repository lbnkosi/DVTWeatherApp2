package com.lbnkosi.weatherapp.data.source.local

import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalWeatherDataSource @Inject constructor(private val weatherDataDao: WeatherDataDao) {

    /**
     * Get cached weather data
     */
    suspend fun getCachedWeatherData(): Flow<WeatherDataEntity?> {
        val weatherData = weatherDataDao.getWeatherDataEntity()
        return flow { emit(weatherData) }
    }

    /**
     * Create and store weather data to the db
     */
    suspend fun createCacheWeatherData(weatherData: LocalWeatherData): Flow<WeatherDataEntity?> {
        weatherDataDao.createWeatherData(weatherData.map())
        return flow { emit(weatherDataDao.getWeatherDataEntity()) }
    }

    /**
     * Delete weather data
     */
    suspend fun deleteCachedWeatherData() {
        weatherDataDao.deleteWeatherData()
    }

}