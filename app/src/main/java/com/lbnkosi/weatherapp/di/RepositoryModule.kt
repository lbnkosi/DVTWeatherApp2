package com.lbnkosi.weatherapp.di

import com.google.android.libraries.places.api.net.PlacesClient
import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.repository.MapsRepository
import com.lbnkosi.weatherapp.data.repository.PlacesRepository
import com.lbnkosi.weatherapp.data.repository.WeatherDataRepository
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import com.lbnkosi.weatherapp.data.source.local.LocalWeatherDataSource
import com.lbnkosi.weatherapp.data.source.local.PlacesDataSource
import com.lbnkosi.weatherapp.data.source.remote.MapsDataSource
import com.lbnkosi.weatherapp.data.source.remote.RemoteWeatherDataSource
import com.lbnkosi.weatherapp.domain.usecase.MapsUseCase
import com.lbnkosi.weatherapp.domain.usecase.PlacesUseCase
import com.lbnkosi.weatherapp.domain.usecase.WeatherDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesWeatherDataUseCase(weatherDataRepository: WeatherDataRepository): WeatherDataUseCase = WeatherDataUseCase(weatherDataRepository)

    @Provides
    fun providesWeatherDataRemoteDataSource(weatherMapApiService: OpenWeatherMapApiService): RemoteWeatherDataSource = RemoteWeatherDataSource(weatherMapApiService)

    @Provides
    fun providersWeatherDataLocalDataSource(weatherDataDao: WeatherDataDao): LocalWeatherDataSource = LocalWeatherDataSource(weatherDataDao)

    @Singleton
    @Provides
    fun providesPlacesUseCase(placesRepository: PlacesRepository): PlacesUseCase = PlacesUseCase(placesRepository)

    @Provides
    fun providesPlacesDataSource(placesDao: PlacesDao): PlacesDataSource = PlacesDataSource(placesDao)

    @Singleton
    @Provides
    fun providesMapsUseCase(mapsRepository: MapsRepository): MapsUseCase = MapsUseCase(mapsRepository)

    @Provides
    fun providesMapsDataSource(placesClient: PlacesClient): MapsDataSource = MapsDataSource(placesClient)

}