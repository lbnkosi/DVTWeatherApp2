package com.lbnkosi.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.lbnkosi.weatherapp.data.db.weather.AppDatabase
import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.db.places.PlacesDatabase
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providesWeatherDataDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesWeatherDataDao(database: AppDatabase): WeatherDataDao {
        return database.weatherDataDao()
    }

    @Singleton
    @Provides
    fun providesPlacesDatabase(@ApplicationContext context: Context): PlacesDatabase {
        return Room.databaseBuilder(context, PlacesDatabase::class.java, PlacesDatabase.PLACES_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesPlacesDao(database: PlacesDatabase): PlacesDao {
        return database.placesDao()
    }

}