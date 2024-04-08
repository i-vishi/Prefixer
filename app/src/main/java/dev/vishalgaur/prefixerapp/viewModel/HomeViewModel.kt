package dev.vishalgaur.prefixerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.vishalgaur.prefixerapp.core.base.Result
import dev.vishalgaur.prefixerapp.core.utils.DateUtils
import dev.vishalgaur.prefixerapp.repository.CitySearchRepoImpl
import dev.vishalgaur.prefixerapp.repository.WeatherRepoImpl
import dev.vishalgaur.prefixerapp.ui.model.CitySearchModel
import dev.vishalgaur.prefixerapp.ui.model.CityWeatherData
import dev.vishalgaur.prefixerapp.ui.model.ForecastData
import dev.vishalgaur.prefixerapp.ui.model.HomeUiData
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeViewModel(
    private val weatherRepo: WeatherRepoImpl,
    private val cityRepo: CitySearchRepoImpl,
) : ViewModel() {

    private var _homeUiDataMLD = MutableLiveData(HomeUiData())
    val homeUiDataMLD: LiveData<HomeUiData> get() = _homeUiDataMLD

    private var _citySearchMLD = MutableLiveData<CitySearchModel?>(null)
    val citySearchMLD: LiveData<CitySearchModel?> get() = _citySearchMLD

    fun getWeatherData(cityName: String = CITY_NAME) {
        val location = cityName.ifBlank { CITY_NAME }
        viewModelScope.launch {
            _homeUiDataMLD.value = HomeUiData(isLoading = true)

            try {
                val cityWeatherResponse = weatherRepo.getCurrentWeatherDataByCityName(location)
                val forecastResponse = weatherRepo.getCityWeatherForecast(location)

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
                        searchError = null,
                        isSearching = false,
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

    fun searchCity(query: String) {
        viewModelScope.launch {
            _citySearchMLD.value = null
            _homeUiDataMLD.value = homeUiDataMLD.value?.copy(isSearching = true)
            try {
                when (val citySearchResponse = cityRepo.searchACity(query)) {
                    is Result.Error -> {
                        _homeUiDataMLD.value = homeUiDataMLD.value?.copy(
                            isSearching = false,
                            searchError = citySearchResponse.exception.message,
                        )
                    }

                    Result.Loading -> {
                        _homeUiDataMLD.value = homeUiDataMLD.value?.copy(isSearching = true)
                    }

                    is Result.Success -> {
                        val cityData = citySearchResponse.data.firstOrNull()
                        if (cityData != null) {
                            _citySearchMLD.value = CitySearchModel(
                                name = cityData.name,
                                lat = cityData.latitude,
                                long = cityData.longitude,
                            )
                            _homeUiDataMLD.value = homeUiDataMLD.value?.copy(
                                isSearching = false,
                                searchError = null,
                            )
                        } else {
                            _homeUiDataMLD.value = homeUiDataMLD.value?.copy(
                                isSearching = false,
                                searchError = "No City Found",
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString(), e)
                _homeUiDataMLD.value = homeUiDataMLD.value?.copy(searchError = e.message.toString())
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
        private const val CITY_NAME = "Bangalore"

        fun provideFactory(
            weatherRepo: WeatherRepoImpl,
            cityRepo: CitySearchRepoImpl,
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(weatherRepo = weatherRepo, cityRepo = cityRepo) as T
                }
            }
    }
}
