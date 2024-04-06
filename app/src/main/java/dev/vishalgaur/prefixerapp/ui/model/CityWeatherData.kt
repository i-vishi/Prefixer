package dev.vishalgaur.prefixerapp.ui.model

import androidx.annotation.Keep

@Keep
data class CityWeatherData(
    val cityName: String,
    val currTemperature: Int,
)
