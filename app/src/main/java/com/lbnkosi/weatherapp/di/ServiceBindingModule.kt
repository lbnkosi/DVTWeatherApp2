package com.lbnkosi.weatherapp.di

import com.lbnkosi.weatherapp.domain.repository.IMapsRepository
import com.lbnkosi.weatherapp.domain.repository.IPlacesRepository
import com.lbnkosi.weatherapp.domain.repository.IWeatherDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceBindingModule {

    @Singleton
    @Binds
    abstract fun bindWeatherDataRepository(weatherDataRepository: IWeatherDataRepository): IWeatherDataRepository

    @Singleton
    @Binds
    abstract fun bindPlacesRepository(placesRepository: IPlacesRepository): IPlacesRepository

    @Singleton
    @Binds
    abstract fun bindMapsRepository(mapsRepository: IMapsRepository): IMapsRepository

}