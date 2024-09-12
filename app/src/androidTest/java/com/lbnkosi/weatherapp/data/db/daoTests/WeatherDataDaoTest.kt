package com.lbnkosi.weatherapp.data.db.daoTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lbnkosi.weatherapp.data.db.weather.AppDatabase
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherDataDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var weatherDataDao: WeatherDataDao

    @Before
    fun setup() {
        // Create an in-memory version of the Room database
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
    fun testInsertAndGetWeatherData() = runBlocking {
        // Given
        val weatherData = WeatherDataEntity(id = 1, weatherData = WeatherData(currentTemperature = 123))

        // When
        weatherDataDao.createWeatherData(weatherData)

        // Then
        val retrievedWeatherData = weatherDataDao.getWeatherDataEntity()
        Assert.assertNotNull(retrievedWeatherData)
        Assert.assertEquals(weatherData.id, retrievedWeatherData?.id)
        Assert.assertTrue(weatherData.weatherData?.currentTemperature == retrievedWeatherData?.weatherData?.currentTemperature)
    }

    @Test
    fun testDeleteWeatherData() = runBlocking {
        // Given
        val weatherData = WeatherDataEntity(id = 1, weatherData = WeatherData(currentTemperature = 123))
        weatherDataDao.createWeatherData(weatherData)

        // When
        weatherDataDao.deleteWeatherData()

        // Then
        val retrievedWeatherData = weatherDataDao.getWeatherDataEntity()
        Assert.assertNull(retrievedWeatherData)
    }
}