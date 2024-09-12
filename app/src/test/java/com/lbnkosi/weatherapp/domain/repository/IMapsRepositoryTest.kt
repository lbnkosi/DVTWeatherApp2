package com.lbnkosi.weatherapp.domain.repository

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.domain.models.places.Place
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class IMapsRepositoryTest {

    @Mock
    lateinit var mapsRepository: IMapsRepository

    @Mock
    lateinit var mockPlace: Place

    @Mock
    lateinit var mockAutocompletePrediction: AutocompletePrediction

    @Before
    fun setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getAutocompletePredictions returns predictions`(): Unit = runBlocking {
        // Given
        val query = "Sydney"
        val predictions = listOf(mockAutocompletePrediction)

        // When
        `when`(mapsRepository.getAutocompletePredictions(query)).thenReturn(predictions)

        // Act
        val result = mapsRepository.getAutocompletePredictions(query)

        // Then
        assertEquals(predictions, result)
        verify(mapsRepository).getAutocompletePredictions(query)
    }

    @Test
    fun `test getAutocompletePredictions returns null`(): Unit = runBlocking {
        // Given
        val query = "InvalidQuery"

        // When
        `when`(mapsRepository.getAutocompletePredictions(query)).thenReturn(null)

        // Act
        val result = mapsRepository.getAutocompletePredictions(query)

        // Then
        assertNull(result)
        verify(mapsRepository).getAutocompletePredictions(query)
    }

    @Test
    fun `test getMapsPlace returns a place`(): Unit = runBlocking {
        // Given
        val placeId = "ChIJN1t_tDeuEmsRUsoyG83frY4"

        // When
        `when`(mapsRepository.getMapsPlace(placeId)).thenReturn(mockPlace)

        // Act
        val result = mapsRepository.getMapsPlace(placeId)

        // Then
        assertEquals(mockPlace, result)
        verify(mapsRepository).getMapsPlace(placeId)
    }

    @Test
    fun `test getMapsPlace returns null`(): Unit = runBlocking {
        // Given
        val placeId = "InvalidPlaceId"

        // When
        `when`(mapsRepository.getMapsPlace(placeId)).thenReturn(null)

        // Act
        val result = mapsRepository.getMapsPlace(placeId)

        // Then
        assertNull(result)
        verify(mapsRepository).getMapsPlace(placeId)
    }
}