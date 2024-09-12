package com.lbnkosi.weatherapp.data.repository

import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.data.source.local.PlacesDataSource
import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlacesRepositoryTest {

    @Mock
    private lateinit var dataSource: PlacesDataSource

    private lateinit var placesRepository: PlacesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        placesRepository = PlacesRepository(dataSource)
    }

    @Test
    fun `test getPlaces returns correct data`() = runTest {
        // Given
        val placeList = arrayListOf(PlacesEntity(id = 1, name = "Place 1"))
        val flowResponse = flowOf(placeList)

        whenever(dataSource.getPlaces()).thenReturn(flowResponse)

        // When
        val result = placesRepository.getPlaces().first()

        // Then
        assert(result.isNotEmpty())
        assert(result[0].id == 1)
        assert(result[0].name == "Place 1")
    }

    @Test
    fun `test getPlaceById returns correct data`() = runTest {
        // Given
        val id = "123 Main St"
        val place = PlacesEntity(id = 1, name = "Place 1", description = id)
        val flowResponse = flowOf(place)

        whenever(dataSource.getPlaceById(id)).thenReturn(flowResponse)

        // When
        val result = placesRepository.getPlaceById(id).first()

        // Then
        assert(result?.id == 1)
        assert(result?.description == id)
    }

    @Test
    fun `test createPlace stores data`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1")
        val placeEntity = PlacesEntity(id = 1, name = "Place 1")
        val flowResponse = flowOf(arrayListOf(placeEntity))

        whenever(dataSource.createPlace(place)).thenReturn(flowResponse)

        // When
        val result = placesRepository.createPlace(place).first()

        // Then
        assert(result.isNotEmpty())
        assert(result[0].id == 1)
        assert(result[0].name == "Place 1")

        // Verify
        verify(dataSource).createPlace(place)
    }

    @Test
    fun `test updatePlace updates data`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1")
        val placeEntity = PlacesEntity(id = 1, name = "Place 1")
        val flowResponse = flowOf(arrayListOf(placeEntity))

        whenever(dataSource.updatePlace(place)).thenReturn(flowResponse)

        // When
        val result = placesRepository.updatePlace(place).first()

        // Then
        assert(result.isNotEmpty())
        assert(result[0].id == 1)
        assert(result[0].name == "Place 1")

        // Verify
        verify(dataSource).updatePlace(place)
    }

    @Test
    fun `test deleteAllPlaces clears data`() = runTest {
        // Given
        val emptyList = arrayListOf<PlacesEntity>()
        val flowResponse = flowOf(emptyList)

        whenever(dataSource.deleteAllPlaces()).thenReturn(flowResponse)

        // When
        val result = placesRepository.deleteAllPlaces().first()

        // Then
        assert(result.isEmpty())

        // Verify
        verify(dataSource).deleteAllPlaces()
    }

    @Test
    fun `test deletePlace removes data`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1")
        val placeEntity = PlacesEntity(id = 1, name = "Place 1")
        val flowResponse = flowOf(arrayListOf(placeEntity))

        whenever(dataSource.deletePlace(place)).thenReturn(flowResponse)

        // When
        val result = placesRepository.deletePlace(place).first()

        // Then
        assert(result.isNotEmpty())
        assert(result[0].id == 1)

        // Verify
        verify(dataSource).deletePlace(place)
    }

    @Test
    fun `test deletePlaces removes multiple places`() = runTest {
        // Given
        val places = arrayListOf(
            Place(id = 1, name = "Place 1"),
            Place(id = 2, name = "Place 2")
        )

        // When
        placesRepository.deletePlaces(places)

        // Then
        // Verify
        verify(dataSource).deletePlaces(places)
    }
}