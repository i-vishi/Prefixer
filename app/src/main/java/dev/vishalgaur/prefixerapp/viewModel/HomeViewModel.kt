package dev.vishalgaur.prefixerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.core.utils.DateUtils
import dev.vishalgaur.prefixerapp.repository.WeatherRepoImpl
import dev.vishalgaur.prefixerapp.ui.model.CityWeatherData
import dev.vishalgaur.prefixerapp.ui.model.ForecastData
import dev.vishalgaur.prefixerapp.ui.model.HomeUiData
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeViewModel(private val repo: WeatherRepoImpl) : ViewModel() {

    private var _homeUiDataMLD = MutableLiveData(HomeUiData())
    val homeUiDataMLD: LiveData<HomeUiData> get() = _homeUiDataMLD

    fun getWeatherData(cityName: String = CITY_NAME) {
        val location = cityName.ifBlank { CITY_NAME }
        viewModelScope.launch {
            _homeUiDataMLD.value = HomeUiData(isLoading = true)

            try {
                val cityWeatherResponse = repo.getCurrentWeatherDataByCityName(location)
                val forecastResponse = repo.getCityWeatherForecast(location)

                var cityWeatherData: CityWeatherData? = null
                var forecastData = mutableListOf<ForecastData>()

                when (cityWeatherResponse) {
                    is Result.Success -> {
                        cityWeatherData = CityWeatherData(
                            cityName = cityWeatherResponse.data.cityName,
                            currTemperature = cityWeatherResponse.data.main.temp.roundToInt(),
                        )
                    }

                    is Result.Error -> {
                        _homeUiDataMLD.value = HomeUiData(
                            isLoading = false,
                            error = cityWeatherResponse.exception.message,
                        )
                    }

                    else -> { // Do Nothing
                    }
                }

                when (forecastResponse) {
                    is Result.Success -> {
                        forecastData = forecastResponse.data.map {
                            ForecastData(
                                weekDay = DateUtils.getWeekDayFromDateString(it.date),
                                temperature = it.main.temp.roundToInt(),
                            )
                        }.toMutableList()
                    }

                    is Result.Error -> {
                        _homeUiDataMLD.value = HomeUiData(
                            isLoading = false,
                            error = forecastResponse.exception.message,
                        )
                    }

                    else -> { // Do Nothing
                    }
                }

                if (cityWeatherData != null && forecastData.isNotEmpty()) {
                    _homeUiDataMLD.value = HomeUiData(
                        todayData = cityWeatherData,
                        forecastList = forecastData,
                        isLoading = false,
                        error = null,
                    )
                } else {
                    _homeUiDataMLD.value =
                        HomeUiData(isLoading = false, error = "Some Error Occurred")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString(), e)
                _homeUiDataMLD.value = HomeUiData(isLoading = false, error = e.message.toString())
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
        private const val CITY_NAME = "Bangalore"

        fun provideFactory(repo: WeatherRepoImpl): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(repo = repo) as T
                }
            }
    }
}
