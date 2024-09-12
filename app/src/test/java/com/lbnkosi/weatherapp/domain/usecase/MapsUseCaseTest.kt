package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.domain.repository.IMapsRepository
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.domain.models.places.Place
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MapsUseCaseTest {

    @Mock
    lateinit var mapsRepository: IMapsRepository

    @Mock
    lateinit var mockPlace: Place

    @Mock
    lateinit var mockAutocompletePrediction: AutocompletePrediction

    private lateinit var mapsUseCase: MapsUseCase
    private lateinit var predictionsList: List<AutocompletePrediction>

    @Before
    fun setup() {
        // Initialize Mockito and the mocks
        MockitoAnnotations.openMocks(this)
        mapsUseCase = MapsUseCase(mapsRepository)

        // Initialize predictions list
        predictionsList = listOf(mockAutocompletePrediction)
    }

    @Test
    fun `test getAutocompletePredictions returns predictions`(): Unit = runBlocking {
        // Given
        val query = "Sydney"
        `when`(mapsRepository.getAutocompletePredictions(query)).thenReturn(predictionsList)

        // Act
        val result = mapsUseCase.getAutocompletePredictions(query)

        // Then
        assertEquals(predictionsList, result)
        verify(mapsRepository).getAutocompletePredictions(query)
    }

    @Test
    fun `test getAutocompletePredictions returns null`(): Unit = runBlocking {
        // Given
        val query = "InvalidQuery"
        `when`(mapsRepository.getAutocompletePredictions(query)).thenReturn(null)

        // Act
        val result = mapsUseCase.getAutocompletePredictions(query)

        // Then
        assertNull(result)
        verify(mapsRepository).getAutocompletePredictions(query)
    }

    @Test
    fun `test getMapsPlace returns a place`(): Unit = runBlocking {
        // Given
        val placeId = "ChIJN1t_tDeuEmsRUsoyG83frY4"
        `when`(mapsRepository.getMapsPlace(placeId)).thenReturn(mockPlace)

        // Act
        val result = mapsUseCase.getMapsPlace(placeId)

        // Then
        assertEquals(mockPlace, result)
        verify(mapsRepository).getMapsPlace(placeId)
    }

    @Test
    fun `test getMapsPlace returns null`(): Unit = runBlocking {
        // Given
        val placeId = "InvalidPlaceId"
        `when`(mapsRepository.getMapsPlace(placeId)).thenReturn(null)

        // Act
        val result = mapsUseCase.getMapsPlace(placeId)

        // Then
        assertNull(result)
        verify(mapsRepository).getMapsPlace(placeId)
    }
}