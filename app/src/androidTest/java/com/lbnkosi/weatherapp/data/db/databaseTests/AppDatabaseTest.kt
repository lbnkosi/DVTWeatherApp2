package com.lbnkosi.weatherapp.data.db.databaseTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lbnkosi.weatherapp.data.db.weather.AppDatabase
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.domain.enums.WeatherType
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var weatherDataDao: WeatherDataDao

    @Before
    fun setup() {
        // Initialize an in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        weatherDataDao = database.weatherDataDao()
    }

    @After
    fun teardown() {
        // Close the database after each test
        database.close()
    }

    @Test
    fun testDatabaseCreation() {
        // Test if the database and DAO are instantiated correctly
        Assert.assertNotNull(database)
        Assert.assertNotNull(weatherDataDao)
    }

    @Test
    fun testInsertAndRetrieveWeatherData() = runBlocking {
        // Given a WeatherDataEntity
        val weatherData = WeatherDataEntity(
            id = 1,
            weatherData = WeatherData(weatherType = WeatherType.Sunny)
        )

        // When inserting the weather data
        weatherDataDao.createWeatherData(weatherData)

        // Then retrieving the weather data should return the correct entity
        val retrievedWeatherData = weatherDataDao.getWeatherDataEntity()
        Assert.assertNotNull(retrievedWeatherData)
        Assert.assertEquals(weatherData.id, retrievedWeatherData?.id)
        Assert.assertEquals(weatherData.weatherData?.weatherType, retrievedWeatherData?.weatherData?.weatherType)
    }

    @Test
    fun testDeleteWeatherData() = runBlocking {
        // Given a WeatherDataEntity
        val weatherData = WeatherDataEntity(
            id = 1,
            weatherData = WeatherData(weatherType = WeatherType.Sunny)
        )

        // When inserting the weather data and then deleting it
        weatherDataDao.createWeatherData(weatherData)
        weatherDataDao.deleteWeatherData()

        // Then retrieving the weather data should return null
        val retrievedWeatherData = weatherDataDao.getWeatherDataEntity()
        Assert.assertNull(retrievedWeatherData)
    }
}