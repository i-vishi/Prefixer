package dev.vishalgaur.prefixerapp.ui.model

import androidx.annotation.Keep

@Keep
data class ForecastData(
    val weekDay: String,
    val temperature: Int,
)
