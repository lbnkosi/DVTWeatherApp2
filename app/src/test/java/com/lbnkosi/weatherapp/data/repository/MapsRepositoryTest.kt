package com.lbnkosi.weatherapp.data.repository

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.source.remote.MapsDataSource
import com.lbnkosi.weatherapp.domain.models.places.Place
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MapsRepositoryTest {

    @Mock
    private lateinit var dataSource: MapsDataSource

    private lateinit var mapsRepository: MapsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapsRepository = MapsRepository(dataSource)
    }

    @Test
    fun `test getAutocompletePredictions returns correct data`() = runTest {
        // Given
        val query = "New York"
        val prediction1 = mock<AutocompletePrediction> {
            on { placeId } doReturn "1"
        }
        val prediction2 = mock<AutocompletePrediction> {
            on { placeId } doReturn "2"
        }
        val predictions = listOf(prediction1, prediction2)
        val resource = Resource.success(predictions)

        whenever(dataSource.getAutocompletePredictions(query)).thenReturn(resource)

        // When
        val result = mapsRepository.getAutocompletePredictions(query)

        // Then
        assert(result?.size == 2)
        assert(result?.get(0)?.placeId == "1")

        // Verify
        verify(dataSource).getAutocompletePredictions(query)
    }

    @Test
    fun `test getMapsPlace returns correct data`() = runTest {
        // Given
        val placeId = "ChIJN1t_tDeuEmsRUsoyG83frY4"
        val place = Place(
            name = "Sydney",
        )
        val resource = Resource.success(place)

        whenever(dataSource.getMapsPlace(placeId)).thenReturn(resource)

        // When
        val result = mapsRepository.getMapsPlace(placeId)

        // Then
        assert(result?.name == "Sydney")

        // Verify
        verify(dataSource).getMapsPlace(placeId)
    }
}