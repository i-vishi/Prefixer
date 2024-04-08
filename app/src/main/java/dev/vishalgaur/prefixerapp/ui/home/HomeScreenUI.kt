package dev.vishalgaur.prefixerapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.vishalgaur.prefixerapp.R
import dev.vishalgaur.prefixerapp.ui.model.CityWeatherData
import dev.vishalgaur.prefixerapp.ui.model.ForecastData
import dev.vishalgaur.prefixerapp.ui.model.HomeUiData
import dev.vishalgaur.prefixerapp.ui.theme.PrefixerAppTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreenUI(uiData: HomeUiData, onRetry: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeContent,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        if (uiData.isLoading) {
            HomeLoadingUI(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            )
        } else if (!uiData.error.isNullOrBlank()) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = uiData.error,
                    actionLabel = "Retry",
                    duration = SnackbarDuration.Long,
                )

                when (result) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        onRetry()
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.temp_with_degree,
                        uiData.todayData?.currTemperature.toString(),
                    ),
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .wrapContentHeight(),
                    style = MaterialTheme.typography.headlineLarge,
                )
                Text(
                    text = uiData.todayData?.cityName ?: "",
                    modifier = Modifier.wrapContentHeight(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                uiData.forecastList?.let { forecasts ->
                    AnimatedVisibility(
                        visible = forecasts.isNotEmpty(),
                        enter = slideInVertically(
                            animationSpec = spring(stiffness = Spring.StiffnessLow),
                            initialOffsetY = { offY ->
                                offY * 2 / 3
                            },
                        ),
                    ) {
                        WeatherForecastView(
                            modifier = Modifier,
                            forecastList = forecasts,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeLoadingUI(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
        ),
        label = "loadingAngle",
    )
    Box(modifier = modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(id = R.drawable.ic_loop_96),
            contentDescription = "Loading",
            modifier = Modifier
                .align(Alignment.Center)
                .size(96.dp)
                .graphicsLayer { rotationZ = angle },
            tint = Color.Black,
        )
    }
}

@Composable
private fun WeatherForecastView(modifier: Modifier, forecastList: List<ForecastData>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            )
            .padding(top = 20.dp)
            .padding(horizontal = 16.dp),
    ) {
        forecastList.forEachIndexed { index, forecastData ->
            key("${forecastData.weekDay}-${forecastData.temperature}") {
                ForecastItemUI(
                    modifier = Modifier.fillMaxWidth(),
                    weekName = forecastData.weekDay,
                    temperature = forecastData.temperature,
                )
            }
            if (index != forecastList.lastIndex) {
                key("divider-${forecastData.weekDay}-${forecastData.temperature}") {
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun ForecastItemUI(modifier: Modifier, weekName: String, temperature: Int) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(vertical = 30.dp),
    ) {
        Text(
            text = weekName,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = stringResource(id = R.string.temp_with_c, temperature),
            modifier = Modifier.wrapContentHeight(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun PreviewHomeScreenUI() {
    PrefixerAppTheme {
        HomeScreenUI(
            uiData = HomeUiData(
                isLoading = false,
                error = null,
                todayData = CityWeatherData(
                    cityName = "Bangalore",
                    currTemperature = 30,
                ),
                forecastList = listOf(
                    ForecastData("Monday", 23),
                    ForecastData("Tuesday", 24),
                    ForecastData("Wednesday", 25),
                    ForecastData("Thursday", 26),
                ),
            ),
            onRetry = {},
        )
    }
}
