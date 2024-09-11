package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.repository.IWeatherDataRepository
import javax.inject.Inject

class WeatherDataUseCase @Inject constructor(private val repository: IWeatherDataRepository) {

    suspend fun getWeatherData(lat: Double, lon: Double) = repository.getWeatherData(lat, lon)

    suspend fun getCachedWeatherData() = repository.getCachedWeatherData()

    suspend fun createCacheWeatherData(localWeatherData: LocalWeatherData) = repository.createCacheWeatherData(localWeatherData)

    suspend fun deleteCachedWeatherData() = repository.deleteCachedWeatherData()

}