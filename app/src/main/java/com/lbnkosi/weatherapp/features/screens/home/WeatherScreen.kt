package com.lbnkosi.weatherapp.features.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lbnkosi.weatherapp.R
import com.lbnkosi.weatherapp.domain.enums.WeatherType
import com.lbnkosi.weatherapp.features.utils.backgroundColour
import com.lbnkosi.weatherapp.features.utils.getBackgroundImage
import com.lbnkosi.weatherapp.features.utils.weatherIcon

/*
@Composable
fun Weather(viewModel: HomeViewModel) {
    var weatherType = WeatherType.Sunny

    val sampleHourlyWeather = listOf(
        Forecast("08:00", 28, WeatherType.Rainy),
        Forecast("09:00", 30, WeatherType.Sunny),
        Forecast("10:00", 32, WeatherType.Cloudy)
    )

    val weatherData = WeatherData(19, 25, 17, sampleHourlyWeather)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(weatherType.backgroundColour())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f / 3f)
        ) {
            Image(
                painter = painterResource(weatherType.getBackgroundImage()),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "${weatherData.currentTemperature}°",
                    style = MaterialTheme.typography.h2,
                    color = Color.White
                )
                Text(
                    text = weatherType.name.uppercase(),
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
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
                Text(text = "${weatherData.minTemperature}° min", color = Color.White)
                Text(text = "${weatherData.currentTemperature}° current", color = Color.White)
                Text(text = "${weatherData.maxTemperature}° max", color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Divider(
                color = Color.Gray,
                thickness = 0.5.dp, // Adjust the thickness as needed
                modifier = Modifier.fillMaxWidth()
            )

            //Spacer(modifier = Modifier.height(1.dp))

            weatherData.forecast.forEach { forecast ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp).padding(horizontal = 32.dp)
                ) {
                    Text(text = forecast.day, style = MaterialTheme.typography.body1, color = Color.White)
                    Image(
                        painter = painterResource(forecast.weatherType.weatherIcon()),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "${forecast.temperature}°", style = MaterialTheme.typography.body1, color = Color.White)
                }
            }
        }
    }
}*/
