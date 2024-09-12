package com.lbnkosi.weatherapp.data.source.localTests

import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import com.lbnkosi.weatherapp.data.mappers.map
import com.lbnkosi.weatherapp.data.mappers.mapPlaces
import com.lbnkosi.weatherapp.data.source.local.PlacesDataSource
import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlacesDataSourceTest {

    @Mock
    private lateinit var placesDao: PlacesDao

    private lateinit var placesDataSource: PlacesDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        placesDataSource = PlacesDataSource(placesDao)
    }

    @Test
    fun `test getPlaces returns correct data`() = runTest {
        // Given
        val placesList = arrayListOf(
            PlacesEntity(id = 1, name = "Place 1", description = "123 Street"),
            PlacesEntity(id = 2, name = "Place 2", description = "456 Avenue")
        )
        whenever(placesDao.getPlaces()).thenReturn(placesList)

        // When
        val result = placesDataSource.getPlaces().first()

        // Then
        assert(result.size == 2)
        assert(result[0].id == 1)
        assert(result[1].name == "Place 2")

        // Verify
        verify(placesDao).getPlaces()
    }

    @Test
    fun `test getPlaceByAddress returns correct data`() = runTest {
        // Given
        val place = PlacesEntity(id = 1, name = "Place 1", description = "123 Street")
        whenever(placesDao.getPlaceById("123 Street")).thenReturn(place)

        // When
        val result = placesDataSource.getPlaceByAddress("123 Street").first()

        // Then
        assert(result?.id == 1)
        assert(result?.name == "Place 1")

        // Verify
        verify(placesDao).getPlaceById("123 Street")
    }

    @Test
    fun `test createPlace stores data correctly`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1", description = "123 Street")
        val placesList = arrayListOf(
            PlacesEntity(id = 1, name = "Place 1", description = "123 Street")
        )
        whenever(placesDao.getPlaces()).thenReturn(placesList)

        // When
        val result = placesDataSource.createPlace(place).first()

        // Then
        assert(result.size == 1)
        assert(result[0].id == 1)

        // Verify
        verify(placesDao).getPlaces()
    }

    @Test
    fun `test updatePlace updates data correctly`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1 Updated", description = "123 Street")
        val placesList = arrayListOf(
            PlacesEntity(id = 1, name = "Place 1 Updated", description = "123 Street")
        )
        whenever(placesDao.getPlaces()).thenReturn(placesList)

        // When
        val result = placesDataSource.updatePlace(place).first()

        // Then
        assert(result.size == 1)
        assert(result[0].name == "Place 1 Updated")

        // Verify
        verify(placesDao).getPlaces()
    }

    @Test
    fun `test deletePlace deletes data correctly`() = runTest {
        // Given
        val place = Place(id = 1, name = "Place 1", description = "123 Street")
        val placesList = arrayListOf(
            PlacesEntity(id = 2, name = "Place 2", description = "456 Avenue")
        )
        whenever(placesDao.getPlaces()).thenReturn(placesList)

        // When
        val result = placesDataSource.deletePlace(place).first()

        // Then
        assert(result.size == 1)
        assert(result[0].id == 2)

        // Verify
        verify(placesDao).deletePlace(place.id)
        verify(placesDao).getPlaces()
    }

    @Test
    fun `test deleteAllPlaces clears data`() = runTest {
        // Given
        val emptyList = arrayListOf<PlacesEntity>()
        whenever(placesDao.getPlaces()).thenReturn(emptyList)

        // When
        val result = placesDataSource.deleteAllPlaces().first()

        // Then
        assert(result.isEmpty())

        // Verify
        verify(placesDao).deleteAllPlaces()
        verify(placesDao).getPlaces()
    }

    @Test
    fun `test deletePlaces deletes multiple places`() = runTest {
        // Given
        val places = arrayListOf(
            Place(id = 1, name = "Place 1"),
            Place(id = 2, name = "Place 2")
        )

        // When
        placesDataSource.deletePlaces(places)
    }
}