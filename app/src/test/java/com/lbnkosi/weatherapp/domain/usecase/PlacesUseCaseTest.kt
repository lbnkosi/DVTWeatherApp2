package com.lbnkosi.weatherapp.domain.usecase

import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.domain.repository.IPlacesRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class PlacesUseCaseTest {

    @Mock
    lateinit var placesRepository: IPlacesRepository

    @Mock
    lateinit var mockPlace: Place

    private lateinit var placesUseCase: PlacesUseCase

    private val placeList = arrayListOf<Place>()

    @Before
    fun setup() {
        // Initialize Mockito and the mocks
        MockitoAnnotations.openMocks(this)
        placesUseCase = PlacesUseCase(placesRepository)

        // Initialize place list
        placeList.add(mockPlace)
    }

    @Test
    fun `test getPlaces returns places`(): Unit = runBlocking {
        // Given
        `when`(placesRepository.getPlaces()).thenReturn(flow { emit(placeList) })

        // Act
        val result = placesUseCase.getPlaces()

        // Then
        assertEquals(placeList, result?.single())
        verify(placesRepository).getPlaces()
    }

    @Test
    fun `test getPlaceById returns a place`(): Unit = runBlocking {
        // Given
        val id = "Some ID"
        `when`(placesRepository.getPlaceById(id)).thenReturn(flow { emit(mockPlace) })

        // Act
        val result = placesUseCase.getPlaceById(id)

        // Then
        assertEquals(mockPlace, result?.single())
        verify(placesRepository).getPlaceById(id)
    }

    @Test
    fun `test createPlace creates a place`(): Unit = runBlocking {
        // Given
        `when`(placesRepository.createPlace(mockPlace)).thenReturn(flow { emit(placeList) })

        // Act
        val result = placesUseCase.createPlace(mockPlace)

        // Then
        assertEquals(placeList, result?.single())
        verify(placesRepository).createPlace(mockPlace)
    }

    @Test
    fun `test updatePlace updates a place`(): Unit = runBlocking {
        // Given
        `when`(placesRepository.updatePlace(mockPlace)).thenReturn(flow { emit(placeList) })

        // Act
        val result = placesUseCase.updatePlace(mockPlace)

        // Then
        assertEquals(placeList, result?.single())
        verify(placesRepository).updatePlace(mockPlace)
    }

    @Test
    fun `test deleteAllPlaces deletes all places`(): Unit = runBlocking {
        // Given
        `when`(placesRepository.deleteAllPlaces()).thenReturn(flow { emit(placeList) })

        // Act
        val result = placesUseCase.deleteAllPlaces()

        // Then
        assertEquals(placeList, result?.single())
        verify(placesRepository).deleteAllPlaces()
    }

    @Test
    fun `test deletePlace deletes a place`(): Unit = runBlocking {
        // Given
        `when`(placesRepository.deletePlace(mockPlace)).thenReturn(flow { emit(placeList) })

        // Act
        val result = placesUseCase.deletePlace(mockPlace)

        // Then
        assertEquals(placeList, result?.single())
        verify(placesRepository).deletePlace(mockPlace)
    }

    @Test
    fun `test deletePlaces deletes multiple places`() = runBlocking {
        // Act
        placesUseCase.deletePlaces(placeList)

        // Then
        verify(placesRepository).deletePlaces(placeList)
    }
}