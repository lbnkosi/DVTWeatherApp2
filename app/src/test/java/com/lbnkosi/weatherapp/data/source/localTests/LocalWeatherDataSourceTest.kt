package com.lbnkosi.weatherapp.data.source.localTests

import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.source.local.LocalWeatherDataSource
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalWeatherDataSourceTest {

    @Mock
    private lateinit var weatherDataDao: WeatherDataDao

    private lateinit var localWeatherDataSource: LocalWeatherDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        localWeatherDataSource = LocalWeatherDataSource(weatherDataDao)
    }

    @Test
    fun `test getCachedWeatherData returns correct data`() = runTest {
        // Given
        val weatherDataEntity = WeatherDataEntity(id = 1, weatherData = WeatherData())
        whenever(weatherDataDao.getWeatherDataEntity()).thenReturn(weatherDataEntity)

        // When
        val result = localWeatherDataSource.getCachedWeatherData().first()

        // Then
        assert(result?.id == 1)
        assert(result?.weatherData != null)

        // Verify
        verify(weatherDataDao).getWeatherDataEntity()
    }

    @Test
    fun `test createCacheWeatherData stores data correctly`() = runTest {
        // Given
        val localWeatherData = LocalWeatherData(id = 1, weatherData = WeatherData())
        val weatherDataEntity = localWeatherData.map()
        whenever(weatherDataDao.getWeatherDataEntity()).thenReturn(weatherDataEntity)

        // When
        val result = localWeatherDataSource.createCacheWeatherData(localWeatherData).first()

        // Then
        assert(result?.id == 1)
        assert(result?.weatherData != null)

        // Verify
        verify(weatherDataDao).getWeatherDataEntity()
    }

    @Test
    fun `test deleteCachedWeatherData deletes data`() = runTest {
        // When
        localWeatherDataSource.deleteCachedWeatherData()

        // Then
        verify(weatherDataDao).deleteWeatherData()
    }
}