package com.lbnkosi.weatherapp.domain.repository

import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import kotlinx.coroutines.flow.Flow

interface IWeatherDataRepository {
    suspend fun getWeatherData(lat: Double, lon: Double): Flow<Resource<OpenWeatherMapResponse?>?>?
    suspend fun getCachedWeatherData(): Flow<LocalWeatherData?>
    suspend fun createCacheWeatherData(localWeatherData: LocalWeatherData): Flow<LocalWeatherData?>
    suspend fun deleteCachedWeatherData()
}