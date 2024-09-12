package com.lbnkosi.weatherapp.data.source.remoteTests

import android.text.SpannableString
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.lbnkosi.weatherapp.data.network.Resource
import com.lbnkosi.weatherapp.data.source.remote.MapsDataSource
import com.lbnkosi.weatherapp.domain.enums.ResourceStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MapsDataSourceTest {

    @Mock
    private lateinit var placesClient: PlacesClient

    private lateinit var mapsDataSource: MapsDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapsDataSource = MapsDataSource(placesClient)
    }

    @Test
    fun `test getAutocompletePredictions returns correct data`() = runTest {
        // Given
        val query = "New York"

        // Mock SpannableString for primaryText
        val spannableString1 = SpannableString("New York, NY")
        val spannableString2 = SpannableString("New York City, NY")

        // Create mock predictions
        val prediction1: AutocompletePrediction = mock {
            on { placeId } doReturn "1"
            on { getPrimaryText(null) } doReturn spannableString1
        }
        val prediction2: AutocompletePrediction = mock {
            on { placeId } doReturn "2"
            on { getPrimaryText(null) } doReturn spannableString2
        }

        val predictions = listOf(prediction1, prediction2)
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()

        // Mock Task
        val mockTask: Task<FindAutocompletePredictionsResponse> = Tasks.forResult(
            mock {
                on { autocompletePredictions } doReturn predictions
            }
        )

        whenever(placesClient.findAutocompletePredictions(request)).thenReturn(mockTask)

        // When
        val result = mapsDataSource.getAutocompletePredictions(query)

        // Then
        assert(result.resourceStatus == ResourceStatus.SUCCESS)
        assert(result.data?.size == 2)
        assert(result.data?.get(0)?.placeId == "1")

        // Verify
        verify(placesClient).findAutocompletePredictions(request)
    }

    @Test
    fun `test getAutocompletePredictions returns error`() = runTest {
        // Given
        val query = "New York"
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()

        // Mock Task failure
        val mockTask: Task<FindAutocompletePredictionsResponse> = Tasks.forException(RuntimeException("Error"))

        whenever(placesClient.findAutocompletePredictions(request)).thenReturn(mockTask)

        // When
        val result = mapsDataSource.getAutocompletePredictions(query)

        // Then
        assert(result.resourceStatus == ResourceStatus.ERROR)
        assert(result.message?.first == "Error fetching autocomplete predictions")

        // Verify
        verify(placesClient).findAutocompletePredictions(request)
    }

    @Test
    fun `test getMapsPlace returns error`() = runTest {
        // Given
        val placeId = "ChIJN1t_tDeuEmsRUsoyG83frY4"
        val request = FetchPlaceRequest.builder(placeId, listOf(Place.Field.LAT_LNG)).build()

        // Mock Task failure
        val mockTask: Task<FetchPlaceResponse> = Tasks.forException(RuntimeException("Error"))

        whenever(placesClient.fetchPlace(request)).thenReturn(mockTask)

        // When
        val result = mapsDataSource.getMapsPlace(placeId)

        // Then
        assert(result.resourceStatus == ResourceStatus.ERROR)
        assert(result.message?.first == "Error fetching place details")

        // Verify
        verify(placesClient).fetchPlace(request)
    }
}