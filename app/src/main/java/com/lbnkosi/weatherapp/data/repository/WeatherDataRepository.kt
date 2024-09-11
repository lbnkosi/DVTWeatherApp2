package com.lbnkosi.weatherapp.data.repository

import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.source.local.LocalWeatherDataSource
import com.lbnkosi.weatherapp.data.source.remote.RemoteWeatherDataSource
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(private val dataSource: RemoteWeatherDataSource, private val localDataSource: LocalWeatherDataSource) : IWeatherDataRepository {

    override suspend fun getWeatherData(lat: Double, lon: Double): Flow<Resource<OpenWeatherMapResponse?>?>? {
        return dataSource.getWeatherData(lat, lon)?.map { resource ->
            resource
        }
    }

    override suspend fun getCachedWeatherData(): Flow<LocalWeatherData?> {
        return localDataSource.getCachedWeatherData().map { it?.map() }
    }

    override suspend fun createCacheWeatherData(localWeatherData: LocalWeatherData): Flow<LocalWeatherData?> {
        return localDataSource.createCacheWeatherData(localWeatherData).map { it?.map() }
    }

    override suspend fun deleteCachedWeatherData() {
        localDataSource.deleteCachedWeatherData()
    }

}