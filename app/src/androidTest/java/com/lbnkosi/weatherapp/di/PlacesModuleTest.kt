package com.lbnkosi.weatherapp.di

import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PlacesModuleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var placesClient: PlacesClient

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testPlacesClient() {
        assertNotNull(placesClient)
    }
}