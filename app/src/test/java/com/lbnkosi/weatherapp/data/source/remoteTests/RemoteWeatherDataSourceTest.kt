package com.lbnkosi.weatherapp.data.source.remoteTests

import app.cash.turbine.test
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.service.ApiUrl
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import com.lbnkosi.weatherapp.data.source.remote.RemoteWeatherDataSource
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.await


/*
@ExperimentalCoroutinesApi
class RemoteWeatherDataSourceTest {

    @Mock
    private lateinit var mockApiService: OpenWeatherMapApiService

    @Mock
    private lateinit var mockCall: Call<OpenWeatherMapResponse>

    private lateinit var remoteWeatherDataSource: RemoteWeatherDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteWeatherDataSource = RemoteWeatherDataSource(mockApiService)
    }

    @Test
    fun `getWeatherData returns success with valid data`(): Unit = runBlocking {
        // Given
        val lat = 40.7128
        val lon = -74.0060
        val mockResponse = OpenWeatherMapResponse() // Assuming you have a mock response setup
        val expectedResource = Resource.success(mockResponse)

        // Mock API response
        `when`(mockApiService.getWeatherData(lat, lon, ApiUrl.APP_ID)).thenReturn(mockCall)
        `when`(mockCall.await()).thenReturn(mockResponse)

        // When
        val resultFlow = remoteWeatherDataSource.getWeatherData(lat, lon)

        // Then: Test the flow using Turbine
        resultFlow?.test {
            // Check if the first emitted value is as expected
            val result = awaitItem()
            assert(result?.resourceStatus == ResourceStatus.SUCCESS)
            assertEquals(expectedResource, result)
            awaitComplete() // Ensure the flow completes
        }
        verify(mockApiService).getWeatherData(lat, lon, ApiUrl.APP_ID)
    }

    @Test
    fun `getWeatherData returns error when api call fails`(): Unit = runBlocking {
        // Given
        val lat = 40.7128
        val lon = -74.0060
        val expectedErrorMessage = "Error message"

        // Mock API failure
        `when`(mockApiService.getWeatherData(lat, lon, ApiUrl.APP_ID)).thenReturn(mockCall)
        `when`(mockCall.await()).thenThrow(RuntimeException(expectedErrorMessage))

        // When
        val resultFlow = remoteWeatherDataSource.getWeatherData(lat, lon)

        // Then: Test the flow using Turbine
        resultFlow?.test {
            // Check if the first emitted value is an error
            val result = awaitItem()
            assert(result?.resourceStatus == ResourceStatus.ERROR)
            awaitComplete() // Ensure the flow completes
        }
        verify(mockApiService).getWeatherData(lat, lon, ApiUrl.APP_ID)
    }
}*/
