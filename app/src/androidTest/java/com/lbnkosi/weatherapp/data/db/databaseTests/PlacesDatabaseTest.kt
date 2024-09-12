package com.lbnkosi.weatherapp.data.db.databaseTests

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
class PlacesDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PlacesDatabase
    private lateinit var placesDao: PlacesDao

    @Before
    fun setup() {
        // Initialize the in-memory database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlacesDatabase::class.java
        ).allowMainThreadQueries().build()

        placesDao = database.placesDao()
    }

    @After
    fun teardown() {
        // Close the database after the tests
        database.close()
    }

    @Test
    fun testDatabaseCreation() {
        // Test if the database and DAO are instantiated correctly
        Assert.assertNotNull(database)
        Assert.assertNotNull(placesDao)
    }

    @Test
    fun testInsertAndRetrievePlace() = runBlocking {
        // Given a place entity
        val place = PlacesEntity(id = 1, name = "123 Street", description = "Test Place 1", lat = 12.34.toString(), long = 56.78.toString())

        // When inserting the place
        placesDao.createPlace(place)

        // Then retrieving the place should return the correct data
        val retrievedPlace = placesDao.getPlace(1)
        Assert.assertNotNull(retrievedPlace)
        Assert.assertEquals(place.id, retrievedPlace.id)
        Assert.assertEquals(place.name, retrievedPlace.name)
        Assert.assertEquals(place.description, retrievedPlace.description)
    }

    @Test
    fun testDeleteAllPlaces() = runBlocking {
        // Given two place entities
        val place1 = PlacesEntity(id = 1, name = "123 Street", description = "Test Place 1", lat = 12.34.toString(), long = 56.78.toString())
        val place2 = PlacesEntity(id = 2, name = "456 Avenue", description = "Test Place 2", lat = 34.56.toString(), long = 78.90.toString())

        // When inserting the places
        placesDao.createPlace(place1)
        placesDao.createPlace(place2)

        // Delete all places
        placesDao.deleteAllPlaces()

        // Then retrieving the places should return an empty list
        val allPlaces = placesDao.getPlaces()
        Assert.assertTrue(allPlaces.isEmpty())
    }
}