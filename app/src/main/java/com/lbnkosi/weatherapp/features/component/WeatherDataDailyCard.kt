package com.lbnkosi.weatherapp.features.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lbnkosi.weatherapp.R

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
            val (title, textDate, textEpisode, image) = createRefs()

            WeatherAppText(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = "Location: $name",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            WeatherAppText(
                modifier = Modifier.constrainAs(textDate) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                },
                text = "Weather: $date",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
            )
            WeatherAppText(
                modifier = Modifier.constrainAs(textEpisode) {
                    top.linkTo(textDate.bottom)
                    start.linkTo(parent.start)
                },
                text = "LatLng: $latlng",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground,
            )

            Image(
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                },
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = "",
                contentScale = ContentScale.Inside
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