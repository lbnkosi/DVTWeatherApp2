package com.lbnkosi.weatherapp.data.service

import com.google.gson.Gson
import com.lbnkosi.weatherapp.domain.models.openweathermaps.Current
import com.lbnkosi.weatherapp.domain.models.openweathermaps.OpenWeatherMapResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherMapApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: OpenWeatherMapApiService

    @Before
    fun setup() {
        // Start MockWebServer
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Initialize Retrofit with the mock server's URL
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create API service instance
        apiService = retrofit.create(OpenWeatherMapApiService::class.java)
    }

    @After
    fun teardown() {
        // Shut down the server after the test
        mockWebServer.shutdown()
    }

    @Test
    fun `test getWeatherData returns correct response`() = runBlocking {
        // Given a mock response from MockWebServer
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(mockOpenWeatherMapResponse()))

        mockWebServer.enqueue(mockResponse)

        // When calling the API service
        val call = apiService.getWeatherData(lat = 40.7128, lon = -74.0060)
        val response = call.execute()

        // Then
        assertEquals(200, response.code())
        val responseBody = response.body()
        assertEquals(40.7128, responseBody?.lat)
        assertEquals(-74.0060, responseBody?.lon)
        assertEquals("America/New_York", responseBody?.timezone)
    }

    private fun mockOpenWeatherMapResponse(): OpenWeatherMapResponse {
        return OpenWeatherMapResponse(
            lat = 40.7128,
            lon = -74.0060,
            timezone = "America/New_York",
            timezoneOffset = -14400,
            current = Current(),
            daily = listOf(),
            hourly = listOf()
        )
    }
}