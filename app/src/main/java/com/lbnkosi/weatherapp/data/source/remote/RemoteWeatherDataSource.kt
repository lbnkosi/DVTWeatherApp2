package com.lbnkosi.weatherapp.data.source.remote

import com.lbnkosi.weatherapp.data.network.BaseRemoteRepository
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.service.ApiUrl
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.await
import javax.inject.Inject

class RemoteWeatherDataSource @Inject constructor(private val apiService: OpenWeatherMapApiService) : BaseRemoteRepository() {

    suspend fun getWeatherData(lat: Double, lon: Double): Flow<Resource<OpenWeatherMapResponse?>?>? {
        val result = safeApiCallNoCache { apiService.getWeatherData(lat, lon, ApiUrl.APP_ID).await() }
        return result
    }

}