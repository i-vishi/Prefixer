package dev.vishalgaur.prefixerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import dev.vishalgaur.prefixerapp.core.PreferencesManager
import dev.vishalgaur.prefixerapp.repository.WeatherRepoImpl
import dev.vishalgaur.prefixerapp.ui.home.HomeScreenUI
import dev.vishalgaur.prefixerapp.ui.model.HomeUiData
import dev.vishalgaur.prefixerapp.ui.theme.PrefixerAppTheme
import dev.vishalgaur.prefixerapp.viewModel.HomeViewModel

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels(
        factoryProducer = { HomeViewModel.provideFactory(WeatherRepoImpl()) },
    )
    private lateinit var savedLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        savedLocation = PreferencesManager.getInstance(this).getCurrentLocation()

        if (::savedLocation.isInitialized) {
            viewModel.getWeatherData(savedLocation)
        }

        setContent {
            PrefixerAppTheme {
                HomeScreenContent()
            }
        }
    }

    @Composable
    private fun HomeScreenContent() {
        val homeUiData = viewModel.homeUiDataMLD.observeAsState()

        HomeScreenUI(
            uiData = homeUiData.value ?: HomeUiData(isLoading = true),
            onRetry = {
                viewModel.getWeatherData(savedLocation)
            },
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWeatherData(savedLocation)
    }
}
