package dev.vishalgaur.prefixerapp.ui.model

import androidx.annotation.Keep

@Keep
data class HomeUiData(
    val todayData: CityWeatherData? = null,
    val forecastList: List<ForecastData>? = null,
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSearching: Boolean = false,
    val searchError: String? = null,
)
