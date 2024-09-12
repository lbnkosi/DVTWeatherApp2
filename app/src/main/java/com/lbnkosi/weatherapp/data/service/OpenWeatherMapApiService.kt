package com.lbnkosi.weatherapp.data.service

import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {

    @GET(ApiUrl.ONE_CALL_PATH)
    fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = ApiUrl.APP_ID
    ): Call<OpenWeatherMapResponse>

}