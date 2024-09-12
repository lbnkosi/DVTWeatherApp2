package com.lbnkosi.weatherapp.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.db.places.PlacesDatabase
import com.lbnkosi.weatherapp.data.db.weather.AppDatabase
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RoomModuleTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var placesDatabase: PlacesDatabase
    private lateinit var weatherDataDao: WeatherDataDao
    private lateinit var placesDao: PlacesDao
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<HiltTestApplication>()

        // In-memory Room databases for testing
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()

        placesDatabase = Room.inMemoryDatabaseBuilder(context, PlacesDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()

        // DAOs from the databases
        weatherDataDao = appDatabase.weatherDataDao()
        placesDao = placesDatabase.placesDao()
    }

    @After
    fun teardown() {
        appDatabase.close()
        placesDatabase.close()
    }

    @Test
    fun testProvidesWeatherDataDatabase() {
        assertNotNull(appDatabase)
        assertNotNull(weatherDataDao)
    }

    @Test
    fun testProvidesPlacesDatabase() {
        assertNotNull(placesDatabase)
        assertNotNull(placesDao)
    }
}