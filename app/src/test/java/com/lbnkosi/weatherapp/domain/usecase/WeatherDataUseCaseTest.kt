package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import com.lbnkosi.weatherapp.domain.models.weatherdata.LocalWeatherData
import com.lbnkosi.weatherapp.domain.repository.IWeatherDataRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class WeatherDataUseCaseTest {

    @Mock
    lateinit var weatherDataRepository: IWeatherDataRepository

    @Mock
    lateinit var mockLocalWeatherData: LocalWeatherData

    private lateinit var mockWeatherDataResponse: OpenWeatherMapResponse

    private lateinit var weatherDataUseCase: WeatherDataUseCase

    @Before
    fun setup() {
        // Initialize Mockito and the mocks
        MockitoAnnotations.openMocks(this)
        weatherDataUseCase = WeatherDataUseCase(weatherDataRepository)

        // Initialize mockWeatherDataResponse
        mockWeatherDataResponse = mock(OpenWeatherMapResponse::class.java)
    }

    @Test
    fun `test getWeatherData returns weather data`(): Unit = runBlocking {
        // Given
        val lat = -33.867
        val lon = 151.207
        val mockResource = Resource.success(mockWeatherDataResponse)
        `when`(weatherDataRepository.getWeatherData(lat, lon)).thenReturn(flow { emit(mockResource) })

        // Act
        val result = weatherDataUseCase.getWeatherData(lat, lon)

        // Then
        assertEquals(mockResource, result?.single())
        verify(weatherDataRepository).getWeatherData(lat, lon)
    }

    @Test
    fun `test getCachedWeatherData returns cached data`(): Unit = runBlocking {
        // Given
        `when`(weatherDataRepository.getCachedWeatherData()).thenReturn(flow { emit(mockLocalWeatherData) })

        // Act
        val result = weatherDataUseCase.getCachedWeatherData()

        // Then
        assertEquals(mockLocalWeatherData, result?.single())
        verify(weatherDataRepository).getCachedWeatherData()
    }

    @Test
    fun `test createCacheWeatherData creates and stores weather data`(): Unit = runBlocking {
        // Given
        `when`(weatherDataRepository.createCacheWeatherData(mockLocalWeatherData)).thenReturn(flow { emit(mockLocalWeatherData) })

        // Act
        val result = weatherDataUseCase.createCacheWeatherData(mockLocalWeatherData)

        // Then
        assertEquals(mockLocalWeatherData, result?.single())
        verify(weatherDataRepository).createCacheWeatherData(mockLocalWeatherData)
    }

    @Test
    fun `test deleteCachedWeatherData deletes weather data`() = runBlocking {
        // Act
        weatherDataUseCase.deleteCachedWeatherData()

        // Then
        verify(weatherDataRepository).deleteCachedWeatherData()
    }
}