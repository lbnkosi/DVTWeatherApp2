package com.lbnkosi.weatherapp.features.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lbnkosi.weatherapp.domain.models.weatherdata.WeatherData
import com.lbnkosi.weatherapp.features.component.WeatherAppScaffold
import com.lbnkosi.weatherapp.features.component.WeatherAppShimmer
import com.lbnkosi.weatherapp.features.utils.backgroundColour
import com.lbnkosi.weatherapp.features.utils.getBackgroundImage
import com.lbnkosi.weatherapp.features.utils.weatherIcon
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val scaffoldState = rememberScaffoldState()
    val viewState = viewModel.uiState.collectAsState().value

    LaunchedEffect(viewModel.uiEvent) {
        launch {
            viewModel.uiEvent.collect {
                when (it) {
                    is HomeViewEvent.SnackBarError -> {
                        scaffoldState.snackbarHostState.showSnackbar(it.message.orEmpty())
                    }
                }
            }
        }
    }

    WeatherAppScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        content = {
            Content(isLoading = viewState.isLoading, data = viewState.data!!)
        }
    )
}

@Composable
fun Content(isLoading: Boolean, data: WeatherData) {

    if (isLoading) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                WeatherAppShimmer()
            }
        }
    } else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(data.weatherType.backgroundColour())
        ) {
            if (data.usingCache == true) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f / 3f)
                        .background(Color.Red),
                    contentAlignment = Alignment.CenterStart // Aligns to the start (left) and centers vertically
                ) {
                    Text(
                        text = "Using offline data",
                        color = Color.White,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(if (data.usingCache == true) 1.3f / 3f else 1.5f / 3f)
            ) {
                Image(
                    painter = painterResource(data.weatherType.getBackgroundImage()),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${data.currentTemperature}°",
                        style = MaterialTheme.typography.h2,
                        color = Color.White
                    )
                    Text(
                        text = data.weatherType.name.uppercase(),
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                    ) {
                        Text(text = "Latitude: ${data.lat}", color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Longitude: ${data.lon}", color = Color.White)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f / 3f)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Text(text = "Feels Like ${data.minTemperature}°", color = Color.White)
                    Text(text = "Actual ${data.currentTemperature}°", color = Color.White)
                    Text(text = "Dew Point ${data.maxTemperature}°", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Divider(
                    color = Color.White,
                    thickness = 0.5.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                data.dailyWeatherData.forEach { forecast ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .padding(horizontal = 32.dp)
                    ) {
                        Text(
                            text = forecast.day,
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                        )

                        Image(
                            painter = painterResource(forecast.weatherType.weatherIcon()),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "${forecast.temperature}°",
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }

}

@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun DetailContentItemViewPreview() {
    HomeScreen(viewModel = hiltViewModel())
}