package com.lbnkosi.weatherapp.di

import com.lbnkosi.weatherapp.data.repository.PlacesRepository
import com.lbnkosi.weatherapp.data.repository.WeatherDataRepository
import com.lbnkosi.weatherapp.domain.usecase.MapsUseCase
import com.google.android.libraries.places.api.net.PlacesClient
import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.db.weather.WeatherDataDao
import com.lbnkosi.weatherapp.data.repository.MapsRepository
import com.lbnkosi.weatherapp.data.service.OpenWeatherMapApiService
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import javax.inject.Singleton

class RepositoryModuleTest {

    @Mock
    lateinit var weatherDataRepository: WeatherDataRepository

    @Mock
    lateinit var weatherMapApiService: OpenWeatherMapApiService

    @Mock
    lateinit var weatherDataDao: WeatherDataDao

    @Mock
    lateinit var placesRepository: PlacesRepository

    @Mock
    lateinit var placesDao: PlacesDao

    @Mock
    lateinit var mapsRepository: MapsRepository

    @Mock
    lateinit var placesClient: PlacesClient

    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test providesWeatherDataUseCase`() {
        val weatherDataUseCase = RepositoryModule.providesWeatherDataUseCase(weatherDataRepository)
        assertNotNull(weatherDataUseCase)

        // Run inside a coroutine since getWeatherData is a suspend function
        runBlocking {
            verify(weatherDataRepository, never()).getWeatherData(anyDouble(), anyDouble()) // or any other method to verify
        }
    }

    @Test
    fun `test providesWeatherDataRemoteDataSource`() {
        val remoteWeatherDataSource = RepositoryModule.providesWeatherDataRemoteDataSource(weatherMapApiService)
        assertNotNull(remoteWeatherDataSource)
    }

    @Test
    fun `test providersWeatherDataLocalDataSource`() {
        val localWeatherDataSource = RepositoryModule.providersWeatherDataLocalDataSource(weatherDataDao)
        assertNotNull(localWeatherDataSource)
    }

    @Test
    fun `test providesPlacesUseCase`() {
        val placesUseCase = RepositoryModule.providesPlacesUseCase(placesRepository)
        assertNotNull(placesUseCase)
    }

    @Test
    fun `test providesPlacesDataSource`() {
        val placesDataSource = RepositoryModule.providesPlacesDataSource(placesDao)
        assertNotNull(placesDataSource)
    }

    @Test
    fun `test providesMapsUseCase`() {
        val mapsUseCase = RepositoryModule.providesMapsUseCase(mapsRepository)
        assertNotNull(mapsUseCase)
    }

    @Test
    fun `test providesMapsDataSource`() {
        val mapsDataSource = RepositoryModule.providesMapsDataSource(placesClient)
        assertNotNull(mapsDataSource)
    }
}