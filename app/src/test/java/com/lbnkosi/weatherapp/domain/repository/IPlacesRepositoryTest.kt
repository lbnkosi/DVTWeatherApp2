package com.lbnkosi.weatherapp.domain.repository

import com.lbnkosi.weatherapp.domain.models.places.Place
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class IPlacesRepositoryTest {

    @Mock
    lateinit var placesRepository: IPlacesRepository

    @Mock
    lateinit var place: Place

    private val placeList = arrayListOf<Place>()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getPlaces returns places list`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<ArrayList<Place>> = flow { emit(placeList) }

        // When
        `when`(placesRepository.getPlaces()).thenReturn(flowResponse)

        // Act
        val result = placesRepository.getPlaces()

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).getPlaces()
    }

    @Test
    fun `test getPlaceByAddress returns place`(): Unit = runBlocking {
        // Given
        val address = "123 Main St"
        val flowResponse: Flow<Place?> = flow { emit(place) }

        // When
        `when`(placesRepository.getPlaceByAddress(address)).thenReturn(flowResponse)

        // Act
        val result = placesRepository.getPlaceByAddress(address)

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).getPlaceByAddress(address)
    }

    @Test
    fun `test createPlace returns updated places list`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<ArrayList<Place>> = flow { emit(placeList) }

        // When
        `when`(placesRepository.createPlace(place)).thenReturn(flowResponse)

        // Act
        val result = placesRepository.createPlace(place)

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).createPlace(place)
    }

    @Test
    fun `test updatePlace returns updated places list`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<ArrayList<Place>> = flow { emit(placeList) }

        // When
        `when`(placesRepository.updatePlace(place)).thenReturn(flowResponse)

        // Act
        val result = placesRepository.updatePlace(place)

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).updatePlace(place)
    }

    @Test
    fun `test deleteAllPlaces returns empty places list`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<ArrayList<Place>> = flow { emit(placeList) }

        // When
        `when`(placesRepository.deleteAllPlaces()).thenReturn(flowResponse)

        // Act
        val result = placesRepository.deleteAllPlaces()

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).deleteAllPlaces()
    }

    @Test
    fun `test deletePlace returns updated places list`(): Unit = runBlocking {
        // Given
        val flowResponse: Flow<ArrayList<Place>> = flow { emit(placeList) }

        // When
        `when`(placesRepository.deletePlace(place)).thenReturn(flowResponse)

        // Act
        val result = placesRepository.deletePlace(place)

        // Then
        assertEquals(flowResponse, result)
        verify(placesRepository).deletePlace(place)
    }

    @Test
    fun `test deletePlaces is invoked`() = runBlocking {
        // Given
        val placesToDelete = arrayListOf(place)

        // When
        placesRepository.deletePlaces(placesToDelete)

        // Then
        verify(placesRepository).deletePlaces(placesToDelete)
    }
}