package com.lbnkosi.weatherapp.domain.repository

import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.repository.IWeatherDataRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class IWeatherDataRepositoryTest {

    @Mock
    lateinit var weatherDataRepository: IWeatherDataRepository

    @Mock
    lateinit var mockResponse: Resource<OpenWeatherMapResponse?>

    @Mock
    lateinit var mockLocalWeatherData: LocalWeatherData

    private val lat = 37.7749
    private val lon = -122.4194

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getWeatherData returns weather data`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<Resource<OpenWeatherMapResponse?>?> = flow { emit(mockResponse) }

        // When
        `when`(weatherDataRepository.getWeatherData(lat, lon)).thenReturn(flowResponse)

        // Act
        val result = weatherDataRepository.getWeatherData(lat, lon)

        // Then
        assertEquals(flowResponse, result)
        verify(weatherDataRepository).getWeatherData(lat, lon)
    }

    @Test
    fun `test getCachedWeatherData returns cached data`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<LocalWeatherData?> = flow { emit(mockLocalWeatherData) }

        // When
        `when`(weatherDataRepository.getCachedWeatherData()).thenReturn(flowResponse)

        // Act
        val result = weatherDataRepository.getCachedWeatherData()

        // Then
        assertEquals(flowResponse, result)
        verify(weatherDataRepository).getCachedWeatherData()
    }

    @Test
    fun `test createCacheWeatherData returns cached data`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<LocalWeatherData?> = flow { emit(mockLocalWeatherData) }

        // When
        `when`(weatherDataRepository.createCacheWeatherData(mockLocalWeatherData)).thenReturn(flowResponse)

        // Act
        val result = weatherDataRepository.createCacheWeatherData(mockLocalWeatherData)

        // Then
        assertEquals(flowResponse, result)
        verify(weatherDataRepository).createCacheWeatherData(mockLocalWeatherData)
    }

    @Test
    fun `test deleteCachedWeatherData performs deletion`() = runBlocking {
        // Act
        weatherDataRepository.deleteCachedWeatherData()

        // Then
        verify(weatherDataRepository).deleteCachedWeatherData()
    }
}