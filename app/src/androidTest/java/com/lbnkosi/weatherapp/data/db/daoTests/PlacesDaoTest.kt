package com.lbnkosi.weatherapp.data.db.daoTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.lbnkosi.weatherapp.data.db.places.PlacesDao
import com.lbnkosi.weatherapp.data.db.places.PlacesDatabase
import com.lbnkosi.weatherapp.data.entity.PlacesEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PlacesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PlacesDatabase
    private lateinit var placesDao: PlacesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PlacesDatabase::class.java).allowMainThreadQueries().build()
        placesDao = database.placesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndGetPlace() = runBlocking {
        // Given
        val place = PlacesEntity(id = 1, name = "123 Street", description = "Test Place", lat = 12.34.toString(), long = 56.78.toString())

        // When
        placesDao.createPlace(place)

        // Then
        val retrievedPlace = placesDao.getPlace(1)
        Assert.assertEquals(place.id, retrievedPlace.id)
        Assert.assertEquals(place.name, retrievedPlace.name)
        Assert.assertEquals(place.description, retrievedPlace.description)
    }

    @Test
    fun testUpdatePlace() = runBlocking {
        // Given
        val place = PlacesEntity(id = 1, name = "123 Street", description = "Test Place", lat = 12.34.toString(), long = 56.78.toString())
        placesDao.createPlace(place)

        // When
        val updatedPlace = place.apply { description = "Updated Place" }
        placesDao.updatePlace(updatedPlace)

        // Then
        val retrievedPlace = placesDao.getPlace(1)
        Assert.assertEquals("Updated Place", retrievedPlace.description)
    }

    @Test
    fun testDeletePlace() = runBlocking {
        // Given
        val place = PlacesEntity(id = 1, name = "123 Street", description = "Test Place", lat = 12.34.toString(), long = 56.78.toString())
        placesDao.createPlace(place)

        // When
        placesDao.deletePlace(1)

        // Then
        val retrievedPlace = placesDao.getPlace(1)
        Assert.assertNull(retrievedPlace)
    }

    @Test
    fun testDeleteAllPlaces() = runBlocking {
        // Given
        val place1 = PlacesEntity(id = 1, name = "123 Street", description = "Test Place 1", lat = 12.34.toString(), long = 56.78.toString())
        val place2 = PlacesEntity(id = 2, name = "456 Avenue", description = "Test Place 2", lat = 34.56.toString(), long = 78.90.toString())
        placesDao.createPlace(place1)
        placesDao.createPlace(place2)

        // When
        placesDao.deleteAllPlaces()

        // Then
        val places = placesDao.getPlaces()
        Assert.assertTrue(places.isEmpty())
    }

    @Test
    fun testGetPlaceByname() = runBlocking {
        // Given
        val place = PlacesEntity(id = 1, name = "123 Street", description = "Test Place", lat = 12.34.toString(), long = 56.78.toString())
        placesDao.createPlace(place)

        // When
        val retrievedPlace = placesDao.getPlaceById("1")

        // Then
        Assert.assertNotNull(retrievedPlace)
        Assert.assertEquals(place.name, retrievedPlace?.name)
    }
}