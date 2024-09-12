package com.lbnkosi.weatherapp.features.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun WeatherDataDailyCard(
    modifier: Modifier = Modifier,
    name: String?,
    date: String?,
    latlng: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        ConstraintLayout(modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
            val (textLocation, textWeather, textLatLng) = createRefs()

            WeatherAppText(
                modifier = Modifier.constrainAs(textLocation) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = "Location: $name",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            WeatherAppText(
                modifier = Modifier.constrainAs(textWeather) {
                    top.linkTo(textLocation.bottom)
                    start.linkTo(parent.start)
                },
                text = "Weather: $date",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
            )
            WeatherAppText(
                modifier = Modifier.constrainAs(textLatLng) {
                    top.linkTo(textWeather.bottom)
                    start.linkTo(parent.start)
                },
                text = "LatLng: $latlng",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    WeatherDataDailyCard(
        modifier = Modifier,
        name = "Weather Data Daily Card",
        date = "2020-03-19",
        latlng = "Something",
    )
}