package com.lbnkosi.weatherapp.features.screens.places

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.lbnkosi.weatherapp.domain.models.places.Place
import com.lbnkosi.weatherapp.features.component.WeatherAppScaffold
import com.lbnkosi.weatherapp.features.component.WeatherAppShimmer
import com.lbnkosi.weatherapp.features.component.WeatherDataDailyCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PlacesScreen(viewModel: PlacesViewModel) {

    val scaffoldState = rememberScaffoldState()
    val viewState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect {
                when (it) {
                    is PlacesViewEvent.SnackBarError -> {
                        scaffoldState.snackbarHostState.showSnackbar(it.message.orEmpty())
                    }

                    is PlacesViewEvent.SnackBarSuccess -> {
                        scaffoldState.snackbarHostState.showSnackbar(it.message.orEmpty())
                    }
                }
            }
        }
    }

    WeatherAppScaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState, content = {
        Content(isLoading = viewState.isLoading, data = viewState.places, viewModel = viewModel)
    })

}

@Composable
private fun Content(isLoading: Boolean, data: List<Place>?, viewModel: PlacesViewModel) {

    var searchQuery by remember { mutableStateOf("") }
    var autocompletePredictions by remember { mutableStateOf<List<AutocompletePrediction>?>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp)
        ) {
            TextField(value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    if (newValue.isNotEmpty()) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val predictions = viewModel.getAutocompletePredictions(newValue)
                            autocompletePredictions = predictions
                        }
                    } else {
                        autocompletePredictions = emptyList()
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp, bottomStart = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp, bottomEnd = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp))
                    .background(Color.White, shape = RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp, bottomStart = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp, bottomEnd = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp)),

                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            autocompletePredictions = emptyList()
                        }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Cancel Icon")
                        }
                    }
                },
                shape = RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp, bottomStart = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp, bottomEnd = if (autocompletePredictions.isNullOrEmpty()) 5.dp else 0.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }

        if (autocompletePredictions?.isNotEmpty() == true) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    autocompletePredictions?.forEach { prediction ->
                        Text(text = prediction.getFullText(null).toString(), modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                coroutineScope.launch(Dispatchers.Main) {
                                    searchQuery = prediction
                                        .getPrimaryText(null)
                                        .toString()
                                    keyboardController?.hide()
                                    autocompletePredictions = emptyList()
                                    val place = viewModel.getMapsPlace(prediction.placeId)
                                    if (place?.lat?.toDouble() != null) {
                                        val latLng = LatLng(place.lat.toDouble(), place.long.toDouble())
                                        selectedPlace = place.apply {
                                            name = prediction
                                                .getSecondaryText(null)
                                                .toString()
                                            description = prediction
                                                .getPrimaryText(null)
                                                .toString()
                                            lat = latLng.latitude.toString()
                                            long = latLng.longitude.toString()
                                        }
                                        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                                    }
                                }
                            })
                        if (prediction.placeId != autocompletePredictions!!.last().placeId) {
                            Divider(
                                color = Color.White, thickness = 0.5.dp, modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White), cameraPositionState = cameraPositionState
            ) {
                selectedPlace?.let {
                    val latLng = LatLng(selectedPlace!!.lat.toDouble(), selectedPlace!!.long.toDouble())
                    Marker(
                        state = MarkerState(position = latLng), title = selectedPlace?.description
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedPlace != null) {
            Button(
                onClick = { viewModel.saveSelectedPlace(selectedPlace!!) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(start = 32.dp, end = 32.dp)
                    .height(45.dp),
            ) {
                Text(text = "Save Place")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        ) {
            if (isLoading) {
                items(10) {
                    WeatherAppShimmer()
                }
            } else {
                items(items = data ?: listOf()) { item ->
                    WeatherDataDailyCard(
                        name = item.name, date = item.description, latlng = "Lat: ${item.lat}, Long: ${item.long}"
                    )
                }
            }
        }
    }
}