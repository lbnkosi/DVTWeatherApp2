package com.lbnkosi.weatherapp.data.repository

import com.lbnkosi.weatherapp.data.entity.WeatherDataEntity
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.source.local.LocalWeatherDataSource
import com.lbnkosi.weatherapp.data.source.remote.RemoteWeatherDataSource
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class WeatherDataRepositoryTest {

    @Mock
    private lateinit var remoteWeatherDataSource: RemoteWeatherDataSource

    @Mock
    private lateinit var localWeatherDataSource: LocalWeatherDataSource

    private lateinit var weatherDataRepository: WeatherDataRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherDataRepository = WeatherDataRepository(remoteWeatherDataSource, localWeatherDataSource)
    }

    @Test
    fun `test getWeatherData returns correct data`() = runTest {
        // Given
        val lat = 12.34
        val lon = 56.78
        val openWeatherMapResponse = OpenWeatherMapResponse(
            lat = lat,
            lon = lon,
            timezone = "GMT",
            daily = listOf(),
            hourly = listOf()
        )
        val resource = Resource.success(openWeatherMapResponse)
        val flowResponse = flowOf(resource)

        whenever(remoteWeatherDataSource.getWeatherData(lat, lon)).thenReturn(flowResponse)

        // When
        val result = weatherDataRepository.getWeatherData(lat, lon)?.first()

        // Then
        assert(result?.resourceStatus == ResourceStatus.SUCCESS)
        //assert((result? == ResourceStatus.SUCCESS).data?.lat == lat)
        assert(result?.data?.lat == lat)
        assert(result?.data?.lon == lon)
    }

    @Test
    fun `test getCachedWeatherData returns correct data`() = runTest {
        // Given
        val localWeatherData = WeatherDataEntity(id = 1, weatherData = WeatherData())
        val flowResponse = flowOf(localWeatherData)

        whenever(localWeatherDataSource.getCachedWeatherData()).thenReturn(flowResponse)

        // When
        val result = weatherDataRepository.getCachedWeatherData().first()

        // Then
        assert(result?.id == 1)
        assert(result?.weatherData != null)
    }

    @Test
    fun `test createCacheWeatherData stores data`() = runTest {
        // Given
        val localWeatherData = LocalWeatherData(id = 1, weatherData = WeatherData())
        val localWeatherData2 = WeatherDataEntity(id = 1, weatherData = WeatherData())
        val flowResponse = flowOf(localWeatherData2)

        whenever(localWeatherDataSource.createCacheWeatherData(localWeatherData)).thenReturn(flowResponse)

        // When
        val result = weatherDataRepository.createCacheWeatherData(localWeatherData).first()

        // Then
        assert(result?.id == 1)
        assert(result?.weatherData != null)

        // Verify
        verify(localWeatherDataSource).createCacheWeatherData(localWeatherData)
    }

    @Test
    fun `test deleteCachedWeatherData calls correct method`() = runTest {
        // When
        weatherDataRepository.deleteCachedWeatherData()

        // Then
        verify(localWeatherDataSource).deleteCachedWeatherData()
    }
}