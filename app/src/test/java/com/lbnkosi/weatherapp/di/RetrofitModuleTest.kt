package com.lbnkosi.weatherapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

class RetrofitModuleTest {

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test provideBaseUrl returns correct url`() {
        val baseUrl = RetrofitModule.provideBaseUrl()
        assertEquals("https://api.openweathermap.org/", baseUrl)  // Make sure this matches your real base URL
    }

    @Test
    fun `test providesOpenWeatherMapApi returns non-null service`() {
        val openWeatherMapApiService = mock(OpenWeatherMapApiService::class.java)
        `when`(retrofit.create(OpenWeatherMapApiService::class.java)).thenReturn(openWeatherMapApiService)

        val result = RetrofitModule.providesOpenWeatherMapApi(retrofit)
        assertNotNull(result)
        assertEquals(openWeatherMapApiService, result)

        verify(retrofit).create(OpenWeatherMapApiService::class.java)
    }

    @Test
    fun `test provideOkHttpClient returns correct configuration`() {
        val okHttpClient = RetrofitModule.provideOkHttpClient()
        assertNotNull(okHttpClient)

        // Check if the client contains StethoInterceptor
        val interceptors = okHttpClient.networkInterceptors
        assert(interceptors.any { it is StethoInterceptor })
    }

    @Test
    fun `test providesRetrofit returns non-null retrofit instance`() {
        val baseUrl = "https://api.openweathermap.org/"
        val retrofit = RetrofitModule.providesRetrofit(okHttpClient, baseUrl)

        assertNotNull(retrofit)
        assertEquals(baseUrl, retrofit.baseUrl().toString())
    }
}