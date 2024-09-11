package com.lbnkosi.weatherapp.features.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.lbnkosi.weatherapp.domain.enums.WeatherType
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

object Utility {

    fun Activity.launchActivity(
        packageName: String,
        className: String,
        flags: Int = -1,
        bundle: Bundle? = null
    ) {
        val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
        if (flags != -1) {
            intent.flags = flags
        }
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    @Composable
    fun getAnimateFloat(): State<Float> {
        val infiniteTransition = rememberInfiniteTransition(label = "")
        return infiniteTransition.animateFloat(
            initialValue = 24.0f,
            targetValue = 48.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 800,
                    delayMillis = 100,
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
    }

    fun getWeatherType(weather: Map<String?, Any?>): WeatherType {
        val weatherMain = weather["main"] as? String ?: return WeatherType.Sunny // default to Sunny if main is null

        return when (weatherMain.lowercase()) {
            "clear" -> WeatherType.Sunny
            "clouds" -> WeatherType.Cloudy
            "rain", "drizzle", "thunderstorm" -> WeatherType.Rainy
            else -> WeatherType.Sunny // default to Sunny for unhandled cases
        }
    }

    fun Double.kelvinToCelsius(): Int {
        val celsius = this - 273.15
        return celsius.toInt()
    }

    fun Int.getDayOfWeekFromTimestamp(): String {
        val dateTime = Instant.ofEpochSecond(this.toLong()).atZone(ZoneId.of("Africa/Johannesburg"))
        val dayOfWeek = dateTime.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    }
}