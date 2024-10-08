package com.lbnkosi.weatherapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lbnkosi.weatherapp.data.service.ApiUrl.BASE_URL
import com.lbnkosi.weatherapp.data.service.ApiUrl.BASE_URL_NAME
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Named(BASE_URL_NAME)
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun providesOpenWeatherMapApi(@Named("WEATHERDATA_RETROFIT") retrofit: Retrofit): OpenWeatherMapApiService {
        return retrofit.create(OpenWeatherMapApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Singleton
    @Provides
    @Named("WEATHERDATA_RETROFIT")
    fun providesRetrofit(okHttpClient: OkHttpClient, @Named(BASE_URL_NAME) baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}